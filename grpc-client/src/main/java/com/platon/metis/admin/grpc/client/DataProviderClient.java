package com.platon.metis.admin.grpc.client;

import com.google.protobuf.ByteString;
import com.platon.metis.admin.common.exception.CallGrpcServiceFailed;
import com.platon.metis.admin.grpc.channel.SimpleChannelManager;
import com.platon.metis.admin.grpc.interceptor.TimeoutInterceptor;
import com.platon.metis.admin.grpc.service.DataProviderGrpc;
import com.platon.metis.admin.grpc.service.DataProviderRpcMessage;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author liushuyu
 * @Date 2021/7/8 18:29
 * @Version
 * @Desc 数据节点服务客户端
 * java服务类：DataProviderGrpc
 * proto文件：data_svc.proto
 */

@Component
@Slf4j
public class DataProviderClient {

    @Resource
    private SimpleChannelManager channelManager;

    @Resource
    private TimeoutInterceptor timeoutInterceptor;

    /**
     * 上传文件到数据节点
     * @param dataNodeHost 数据节点IP
     * @param dataNodePort 数据节点端口
     */
    public DataProviderRpcMessage.UploadReply uploadData(String dataNodeHost, int dataNodePort, String fileName, MultipartFile file) {
        //1.获取rpc连接
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ManagedChannel channel = null;
        try{
            channel = channelManager.buildUploadFileChannel(dataNodeHost, dataNodePort);

            //2.构建response流观察者，将会异步处理响应
            ExtendResponseObserver<DataProviderRpcMessage.UploadReply> responseObserver = new ExtendResponseObserver<DataProviderRpcMessage.UploadReply>() {

                //响应
                private DataProviderRpcMessage.UploadReply response;

                public DataProviderRpcMessage.UploadReply getResponse() {
                    return response;
                }

                @Override
                public void onNext(DataProviderRpcMessage.UploadReply uploadReply) {
                    //5.处理response
                    response = uploadReply;

                    if(!response.getOk()){
                        countDownLatch.countDown();
                    }
                }
                @Override
                public void onError(Throwable t) {
                    log.error("failed to upload file", t);
                    if (response==null) {
                        response = DataProviderRpcMessage.UploadReply.newBuilder().setOk(false).build();
                    }
                    countDownLatch.countDown();
                }
                @Override
                public void onCompleted() {
                    countDownLatch.countDown();
                    log.debug("upload file completed.");
                }
            };


            //3.调用rpc,获取request流观察者
            StreamObserver<DataProviderRpcMessage.UploadRequest> requestObserver = DataProviderGrpc.newStub(channel).uploadData(responseObserver);

            //上传的时候FileInfo中填file_name就行，这里有个要求是先传content，再传meta，服务端以收到meta判断是否结束
            /** 第一次流式传输文件内容 */
            // 定义4M字节数据大小
            int MAX_BUFFER_SIZE = 4 * 1024 * 1024;
            byte[] bytes = new byte[MAX_BUFFER_SIZE];
            BufferedInputStream bufferInputStream = new BufferedInputStream(file.getInputStream());
            //从文件中按字节读取内容，到文件尾部时read方法将返回-1
            int bytesRead;
            while ( (bytesRead = bufferInputStream.read(bytes)) != -1) {
                // 每次发送不大于4M数据
                DataProviderRpcMessage.UploadRequest fileChunk = DataProviderRpcMessage.UploadRequest
                        .newBuilder()
                        .setFileName(fileName)
                        .setContent(ByteString.copyFrom(bytes, 0, bytesRead))
                        .build();
                requestObserver.onNext(fileChunk);
            }

            // Mark the end of requests
            requestObserver.onCompleted();

            try {
                //等待服务端数据返回
                boolean await = countDownLatch.await(timeoutInterceptor.getTimeout()+10, TimeUnit.SECONDS);
                if(!await){
                    log.error("call Carrier RPC uploadData timeout");
                    throw new CallGrpcServiceFailed();
                }
            } catch (InterruptedException e) {
                log.error("call Carrier RPC uploadData error", e);
                throw new CallGrpcServiceFailed();
            }

            if(responseObserver.getResponse() == null
                    || responseObserver.getResponse().getOk()==false
                    || StringUtils.isBlank(responseObserver.getResponse().getDataId())
                    || StringUtils.isBlank(responseObserver.getResponse().getFilePath())) {
                throw new CallGrpcServiceFailed();
            }
            return responseObserver.getResponse();
        } catch (IOException e) {
            log.error("failed to upload file", e);
            throw new CallGrpcServiceFailed();
        } finally {
            channelManager.closeChannel(channel);
        }

    }

    /**
     * 从数据节点下载文件
     * @param dataNodeHost 数据节点IP
     * @param dataNodePort 数据节点端口
     * @param filePath 要下载文件的文件路径
     */
    public byte[] downloadData(String dataNodeHost, int dataNodePort, String filePath){

        //1.获取rpc连接
        ManagedChannel channel = null;
        try{
            channel = channelManager.buildChannel(dataNodeHost, dataNodePort);
            //2.构建请求
            DataProviderRpcMessage.DownloadRequest request = DataProviderRpcMessage.DownloadRequest
                    .newBuilder()
                    .setFilePath(filePath)
                    .build();

            CountDownLatch countDownLatch = new CountDownLatch(1);

            //3.构建response流观察者
            ExtendResponseObserver<DataProviderRpcMessage.DownloadReply> responseObserver = new ExtendResponseObserver<DataProviderRpcMessage.DownloadReply>() {

                public DataProviderRpcMessage.DownloadReply response;
                public DataProviderRpcMessage.DownloadReply getResponse(){
                    return response;
                }

                @Override
                public void onNext(DataProviderRpcMessage.DownloadReply downloadReply) {
                    //5.处理response
                    boolean hasContent = downloadReply.hasContent();
                    if(hasContent){
                        ByteString content = downloadReply.getContent();
                        response.getContent().concat(content);
                    }

                    boolean hasStatus = downloadReply.hasStatus();
                    if(hasStatus){
                        DataProviderRpcMessage.TaskStatus status = downloadReply.getStatus();
                        /**
                         * Start = 0;
                         * Finished = 1;
                         * Cancelled = 2;
                         * Failed = 3;
                         */
                        switch (status.getNumber()){
                            case 0:
                                log.debug("开始下载文件filePath:{}，状态:{}.......",filePath,"Start");
                                response = DataProviderRpcMessage.DownloadReply.newBuilder().setStatus(DataProviderRpcMessage.TaskStatus.Start).build();
                                break;
                            case 1:
                                response.toBuilder().setStatus(DataProviderRpcMessage.TaskStatus.Finished);
                                log.debug("下载完成文件filePath:{}，状态:{}.......",filePath,"Finished");
                                break;
                            case 2:
                                response.toBuilder().setStatus(DataProviderRpcMessage.TaskStatus.Cancelled);
                                break;
                            case 3:
                                response.toBuilder().setStatus(DataProviderRpcMessage.TaskStatus.Failed);
                                break;
                            default:
                                break;
                        }

                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    countDownLatch.countDown();
                    response.toBuilder().setStatus(DataProviderRpcMessage.TaskStatus.Failed);
                }

                @Override
                public void onCompleted() {
                    countDownLatch.countDown();
                }
            };


            //3.调用rpc
            DataProviderGrpc.newStub(channel).downloadData(request,responseObserver);

            try {
                //等待服务端数据返回
                boolean await = countDownLatch.await(timeoutInterceptor.getTimeout()+10, TimeUnit.SECONDS);
                if(!await){
                    log.error("call Carrier RPC downloadData timeout");
                    throw new CallGrpcServiceFailed();
                }
            } catch (InterruptedException e) {
                log.error("call Carrier RPC downloadData error", e);
                throw new CallGrpcServiceFailed();
            }

            if(responseObserver.getResponse() == null
                    || responseObserver.getResponse().getStatus() != DataProviderRpcMessage.TaskStatus.Finished) {
                throw new CallGrpcServiceFailed();
            }
            return responseObserver.getResponse().getContent().toByteArray();

        } finally {
            channelManager.closeChannel(channel);
        }
    }
}