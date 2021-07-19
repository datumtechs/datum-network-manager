package com.platon.rosettanet.admin.grpc.client;

import cn.hutool.core.util.StrUtil;
import com.google.protobuf.ByteString;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.entity.DataProviderUploadDataResp;
import com.platon.rosettanet.admin.grpc.service.DataProviderGrpc;
import com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;


    /**
     * 上传文件到数据节点
     * @param dataNodeHost 数据节点IP
     * @param dataNodePort 数据节点端口
     */
    public DataProviderUploadDataResp uploadData(String dataNodeHost, int dataNodePort, String fileName, byte[] fileContent) throws ApplicationException{
        //1.获取rpc连接
        Channel channel = null;
        try{
            channelManager.buildChannel(dataNodeHost, dataNodePort);
            //2.构建response流观察者
            DataProviderUploadDataResp resp = new DataProviderUploadDataResp();
            StreamObserver<DataProviderRpcMessage.UploadReply> responseObserver = new StreamObserver<DataProviderRpcMessage.UploadReply>() {
                @Override
                public void onNext(DataProviderRpcMessage.UploadReply uploadReply) {
                    //5.处理response
                    if(!uploadReply.getOk()){
                        throw new ApplicationException(StrUtil.format("上传文件失败：文件名:{}",fileName));
                    }
                    //源文件ID
                    String dataId = uploadReply.getDataId();
                    //源文件存储路径
                    String filePath = uploadReply.getFilePath();
                    resp.setFileId(dataId);
                    resp.setFilePath(filePath);
                }

                @Override
                public void onError(Throwable throwable) {
                    throw new ApplicationException(StrUtil.format("上传文件失败：文件名:{}",fileName),throwable);
                }

                @Override
                public void onCompleted() {
                    log.info("文件上传成功：{}",fileName);
                }
            };
            //3.调用rpc,获取request流观察者
            StreamObserver<DataProviderRpcMessage.UploadRequest> requestObserver = DataProviderGrpc.newStub(channel).uploadData(responseObserver);
            //4.将请求内容塞入到请求流中
            //上传的时候FileInfo中填file_name就行，这里有个要求是先传content，再传meta，服务端以收到meta判断是否结束
            //第一次传输
            DataProviderRpcMessage.UploadRequest first = DataProviderRpcMessage.UploadRequest
                    .newBuilder()
                    .setContent(ByteString.copyFrom(fileContent))
                    .build();
            requestObserver.onNext(first);
            //第二次传输
            DataProviderRpcMessage.FileInfo fileInfo = DataProviderRpcMessage.FileInfo
                    .newBuilder()
                    .setFileName(fileName)
                    .build();
            DataProviderRpcMessage.UploadRequest second = DataProviderRpcMessage.UploadRequest
                    .newBuilder()
                    .setContent(ByteString.copyFrom(fileContent))
                    .setMeta(fileInfo)
                    .build();
            requestObserver.onNext(second);
            requestObserver.onCompleted();
            /**
             * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
             * 故将response转换后再放给service类使用
             */
            return resp;
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
    public byte[] downloadData(String dataNodeHost, int dataNodePort, String filePath) throws ApplicationException{
        ByteString result = ByteString.EMPTY;
        //1.获取rpc连接
        Channel channel = null;
        try{
            channelManager.buildChannel(dataNodeHost, dataNodePort);
            //2.构建请求
            DataProviderRpcMessage.DownloadRequest request = DataProviderRpcMessage.DownloadRequest
                    .newBuilder()
                    .setFilePath(filePath)
                    .build();

            //3.构建response流观察者
            StreamObserver<DataProviderRpcMessage.DownloadReply> responseObserver = new StreamObserver<DataProviderRpcMessage.DownloadReply>() {
                @Override
                public void onNext(DataProviderRpcMessage.DownloadReply downloadReply) {
                    //5.处理response
                    boolean hasContent = downloadReply.hasContent();
                    if(hasContent){
                        ByteString content = downloadReply.getContent();
                        result.concat(content);
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
                                log.info("开始下载文件filePath:{}，状态:{}.......",filePath,"Start");
                            case 1:
                                log.info("下载完成文件filePath:{}，状态:{}.......",filePath,"Finished");
                            case 2:
                                throw new ApplicationException(StrUtil.format("下载文件失败：文件名:{},状态:{}",filePath,"Cancelled"));
                            case 3:
                                throw new ApplicationException(StrUtil.format("下载文件失败：文件名:{},状态:{}",filePath,"Failed"));
                            default:
                                break;
                        }

                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    throw new ApplicationException(StrUtil.format("下载文件失败：文件名:{}",filePath),throwable);
                }

                @Override
                public void onCompleted() {
                    log.info("文件下载成功：{}",filePath);
                }
            };
            //3.调用rpc
            DataProviderGrpc.newStub(channel).downloadData(request,responseObserver);
            /**
             * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
             * 故将response转换后再放给service类使用
             */
            return result.toByteArray();
        } finally {
            channelManager.closeChannel(channel);
        }
    }
}
