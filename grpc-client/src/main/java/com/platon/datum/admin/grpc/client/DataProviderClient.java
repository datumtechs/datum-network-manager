package com.platon.datum.admin.grpc.client;

import com.google.protobuf.ByteString;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import com.platon.datum.admin.grpc.common.constant.FighterEnum;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import com.platon.datum.admin.grpc.fighter.api.data.DataProviderGrpc;
import com.platon.datum.admin.grpc.fighter.api.data.DataSvc;
import com.platon.datum.admin.grpc.interceptor.UploadFileTimeoutInterceptor;
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
    private UploadFileTimeoutInterceptor uploadFileTimeoutInterceptor;

    /**
     * 上传文件到数据节点
     *
     * @param dataNodeHost 数据节点IP
     * @param dataNodePort 数据节点端口
     */
    public DataSvc.UploadReply uploadData(String dataNodeHost, int dataNodePort, String fileName, MultipartFile file) {
        log.debug("从carrier上传文件，fileName:{}", fileName);
        //1.获取rpc连接
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ManagedChannel channel = null;
        try {
            channel = channelManager.buildUploadFileChannel(dataNodeHost, dataNodePort);

            //2.构建response流观察者，将会异步处理响应
            ExtendResponseObserver<DataSvc.UploadReply> responseObserver = new ExtendResponseObserver<DataSvc.UploadReply>() {

                //响应
                private DataSvc.UploadReply response;

                @Override
                public DataSvc.UploadReply getResponse() {
                    return response;
                }

                @Override
                public void onNext(DataSvc.UploadReply uploadReply) {
                    //5.处理response
                    response = uploadReply;

                    if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
                        countDownLatch.countDown();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    log.error("failed to upload file", t);
                    if (response == null) {
                        response = DataSvc.UploadReply.newBuilder().setStatus(1).build();
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
            StreamObserver<DataSvc.UploadRequest> requestObserver = DataProviderGrpc.newStub(channel).uploadData(responseObserver);

            //上传的时候FileInfo中填file_name就行，这里有个要求是先传content，再传meta，服务端以收到meta判断是否结束
            /** 第一次流式传输文件内容 */
            // 定义4M字节数据大小
            int MAX_BUFFER_SIZE = 4 * 1024 * 1024;
            byte[] bytes = new byte[MAX_BUFFER_SIZE];
            BufferedInputStream bufferInputStream = new BufferedInputStream(file.getInputStream());
            //从文件中按字节读取内容，到文件尾部时read方法将返回-1
            int bytesRead;
            while ((bytesRead = bufferInputStream.read(bytes)) != -1) {
                // 每次发送不大于4M数据
                DataSvc.UploadRequest fileChunk = DataSvc.UploadRequest
                        .newBuilder()
                        .setDataName(fileName)
                        .setContent(ByteString.copyFrom(bytes, 0, bytesRead))
                        .setDataType(CarrierEnum.OrigindataType.OrigindataType_CSV)//v0.4.0默认csv
                        .build();
                requestObserver.onNext(fileChunk);
            }

            // Mark the end of requests
            requestObserver.onCompleted();

            try {
                //等待服务端数据返回
                boolean await = countDownLatch.await(uploadFileTimeoutInterceptor.getTimeout() + 10, TimeUnit.SECONDS);
                if (!await) {
                    log.error("call Carrier RPC uploadData timeout");
                    throw new CallGrpcServiceFailed();
                }
            } catch (InterruptedException e) {
                log.error("call Carrier RPC uploadData error", e);
                throw new CallGrpcServiceFailed();
            }

            if (responseObserver.getResponse() == null
                    || responseObserver.getResponse().getStatus() != GrpcConstant.GRPC_SUCCESS_CODE
                    || StringUtils.isBlank(responseObserver.getResponse().getDataId())
                    || StringUtils.isBlank(responseObserver.getResponse().getDataPath())) {
                log.error("上传失败: " + responseObserver.getResponse() == null ? "response=null" :
                        "status=" + responseObserver.getResponse().getStatus()
                                + ",dataId=" + responseObserver.getResponse().getDataId()
                                + ",dataPath=" + responseObserver.getResponse().getDataPath());
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
     *
     * @param dataNodeHost 数据节点IP
     * @param dataNodePort 数据节点端口
     * @param filePath     要下载文件的文件路径
     */
    public byte[] downloadData(String dataNodeHost, int dataNodePort, String filePath) {
        log.debug("从carrier下载文件，filePath:{}", filePath);
        //1.获取rpc连接
        ManagedChannel channel = null;
        try {
            channel = channelManager.buildChannel(dataNodeHost, dataNodePort);
            //2.构建请求
            DataSvc.DownloadRequest request = DataSvc.DownloadRequest
                    .newBuilder()
                    .setDataPath(filePath)
                    .build();

            CountDownLatch countDownLatch = new CountDownLatch(1);


            //AtomicReference<ByteString> content = new AtomicReference<>(ByteString.EMPTY);

            //3.构建response流观察者
            ExtendResponseObserver<DataSvc.DownloadReply> responseObserver = new ExtendResponseObserver<DataSvc.DownloadReply>() {
                public DataSvc.DownloadReply response = DataSvc.DownloadReply.newBuilder().build();

                @Override
                public DataSvc.DownloadReply getResponse() {
                    return response;
                }

                @Override
                public void onNext(DataSvc.DownloadReply downloadReply) {
                    //5.处理response
                    if (downloadReply.hasContent()) {
                        response = response.toBuilder().setContent(response.getContent().concat(downloadReply.getContent())).build();
                    }
                    if (downloadReply.hasStatus()) {
                        /**
                         * Start = 0;
                         * Finished = 1;
                         * Cancelled = 2;
                         * Failed = 3;
                         */
                        switch (downloadReply.getStatus().getNumber()) {
                            case 0:
                                log.debug("开始下载文件filePath:{}，状态:{}.......", filePath, "Start");
                                //因为oneof,所以此处可以不设置(即使设置也无妨，因为此次response.content还没数据）
                                response = response.toBuilder().setStatus(FighterEnum.TaskStatus.Start).build();
                                break;
                            case 1:
                                //因为oneof,此处不能设置（因为设置后，会重置response.content)
                                log.debug("下载完成文件filePath:{}，状态:{}.......", filePath, "Finished");
                                response = response.toBuilder().setStatus(FighterEnum.TaskStatus.Finished).build();
                                break;
                            case 2:
                                //因为oneof,必须设置，设置后，会重置response.content
                                response = response.toBuilder().setStatus(FighterEnum.TaskStatus.Cancelled).build();
                                break;
                            case 3:
                                //因为oneof,必须设置，设置后，会重置response.content
                                response = response.toBuilder().setStatus(FighterEnum.TaskStatus.Failed).build();
                                break;
                            default:
                                break;
                        }

                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    //因为oneof,必须设置，设置后，会重置response.content
                    log.error("下载失败：onError", throwable);
                    response = response.toBuilder().setStatus(FighterEnum.TaskStatus.Failed).build();
                    countDownLatch.countDown();
                }

                @Override
                public void onCompleted() {
                    countDownLatch.countDown();
                }
            };


            //3.调用rpc
            DataProviderGrpc.newStub(channel).downloadData(request, responseObserver);

            try {
                //等待服务端数据返回
                boolean await = countDownLatch.await(uploadFileTimeoutInterceptor.getTimeout() + 10, TimeUnit.SECONDS);
                if (!await) {
                    log.error("call Carrier RPC downloadData timeout");
                    throw new CallGrpcServiceFailed();
                }
            } catch (InterruptedException e) {
                log.error("call Carrier RPC downloadData error", e);
                throw new CallGrpcServiceFailed();
            }

            log.error("下载####################filePath:" + filePath + "," +
                    (responseObserver.getResponse() == null ? "response=null" : "response.status=" + responseObserver.getResponse().getStatusValue()));
            //只有出错时，才设置了status
            if (responseObserver.getResponse() == null
                    || responseObserver.getResponse().getStatus() != FighterEnum.TaskStatus.Finished) {
                throw new CallGrpcServiceFailed(responseObserver.getResponse() == null ? "response=null" : "response.status=" + responseObserver.getResponse().getStatusValue());
            }
            return responseObserver.getResponse().getContent().toByteArray();

        } finally {
            channelManager.closeChannel(channel);
        }
    }
}