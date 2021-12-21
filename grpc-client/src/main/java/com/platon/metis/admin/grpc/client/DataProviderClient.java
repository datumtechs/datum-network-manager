package com.platon.metis.admin.grpc.client;

import cn.hutool.core.util.StrUtil;
import com.google.protobuf.ByteString;
import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.grpc.channel.SimpleChannelManager;
import com.platon.metis.admin.grpc.entity.DataProviderUploadDataResp;
import com.platon.metis.admin.grpc.service.DataProviderGrpc;
import com.platon.metis.admin.grpc.service.DataProviderRpcMessage;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

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


    /**
     * 上传文件到数据节点
     * @param dataNodeHost 数据节点IP
     * @param dataNodePort 数据节点端口
     */
    public DataProviderUploadDataResp uploadData(String dataNodeHost, int dataNodePort, String fileName, MultipartFile file) throws Exception{
        byte[] fileContent = file.getBytes();
        //1.获取rpc连接
        CountDownLatch count = new CountDownLatch(1);
        ManagedChannel channel = null;
        try{
            channel = channelManager.buildChannel(dataNodeHost, dataNodePort);
            //2.构建response流观察者
            DataProviderUploadDataResp resp = new DataProviderUploadDataResp();
            AtomicReference<ApplicationException> ex = new AtomicReference<>();
            StreamObserver<DataProviderRpcMessage.UploadReply> responseObserver = new StreamObserver<DataProviderRpcMessage.UploadReply>() {
                @Override
                public void onNext(DataProviderRpcMessage.UploadReply uploadReply) {
                    log.debug("观察者模式执行了onNext-========================:{}", uploadReply.toString());
                    //5.处理response
                    if(!uploadReply.getOk()){
                        ex.set(new ApplicationException(StrUtil.format("上传文件失败：文件名:{}",fileName)));
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
                    log.debug("观察者模式执行了onError-========================:{}", throwable.toString());
                    count.countDown();
                    ex.set(new ApplicationException(StrUtil.format("上传文件失败：文件名:{}",fileName),throwable));
                }
                @Override
                public void onCompleted() {
                    log.debug("观察者模式执行了onCompleted-========================:{}", "观察者模式执行了onCompleted");
                    count.countDown();
                }
            };
            //3.调用rpc,获取request流观察者
            StreamObserver<DataProviderRpcMessage.UploadRequest> requestObserver = DataProviderGrpc.newStub(channel).uploadData(responseObserver);
            //4.将请求内容塞入到请求流中
            //上传的时候FileInfo中填file_name就行，这里有个要求是先传content，再传meta，服务端以收到meta判断是否结束
            /** 第一次传输 */
            // 定义4M字节数据大小
            int MAX_BUFFER_SIZE = 4 * 1024 * 1024;
            byte[] bytes = new byte[MAX_BUFFER_SIZE];
            BufferedInputStream bufferInputStream = new BufferedInputStream(file.getInputStream());
            //从文件中按字节读取内容，到文件尾部时read方法将返回-1
            int bytesRead;
            while ( (bytesRead = bufferInputStream.read(bytes)) != -1) {
                // 每次发送不大于4M数据
                DataProviderRpcMessage.UploadRequest first = DataProviderRpcMessage.UploadRequest
                        .newBuilder()
                        .setContent(ByteString.copyFrom(bytes, 0, bytesRead))
                        .build();
                requestObserver.onNext(first);
            }

            /** 第二次传输 */
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
            try {
                //等待服务端数据返回
                boolean await = count.await(1800, TimeUnit.SECONDS);
                    if(!await){
                    throw new ApplicationException(StrUtil.format("超过等待时间，上传文件失败：文件名:{}",fileName));
                }
            } catch (InterruptedException e) {
                throw new ApplicationException(StrUtil.format("上传文件失败：文件名:{}",fileName),e);
            }
            //如果上传过程中出现异常，则抛异常
            if(ex.get() != null){
                throw ex.get();
            }
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
    public byte[] downloadData(String dataNodeHost, int dataNodePort, String filePath){
        AtomicReference<ByteString> byteString = new AtomicReference<>(ByteString.EMPTY);
        CountDownLatch count = new CountDownLatch(1);
        //1.获取rpc连接
        ManagedChannel channel = null;
        try{
            channel = channelManager.buildChannel(dataNodeHost, dataNodePort);
            //2.构建请求
            DataProviderRpcMessage.DownloadRequest request = DataProviderRpcMessage.DownloadRequest
                    .newBuilder()
                    .setFilePath(filePath)
                    .build();

            //3.构建response流观察者
            AtomicReference<ApplicationException> ex = new AtomicReference<>();
            StreamObserver<DataProviderRpcMessage.DownloadReply> responseObserver = new StreamObserver<DataProviderRpcMessage.DownloadReply>() {
                @Override
                public void onNext(DataProviderRpcMessage.DownloadReply downloadReply) {
                    //5.处理response
                    boolean hasContent = downloadReply.hasContent();
                    if(hasContent){
                        ByteString content = downloadReply.getContent();
                        ByteString bs = byteString.get().concat(content);
                        byteString.set(bs);
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
                                break;
                            case 1:
                                log.debug("下载完成文件filePath:{}，状态:{}.......",filePath,"Finished");
                                break;
                            case 2:
                                ex.set(new ApplicationException(StrUtil.format("下载文件失败：文件路劲:{},状态:{}",filePath,"Cancelled")));
                                break;
                            case 3:
                                ex.set(new ApplicationException(StrUtil.format("下载文件失败：文件路劲:{},状态:{}",filePath,"Failed")));
                                break;
                            default:
                                break;
                        }

                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    count.countDown();
                    ex.set(new ApplicationException(StrUtil.format("下载文件失败：文件路劲:{}",filePath),throwable));
                }

                @Override
                public void onCompleted() {
                    count.countDown();
                }
            };
            //3.调用rpc
            DataProviderGrpc.newStub(channel).downloadData(request,responseObserver);
            /**
             * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
             * 故将response转换后再放给service类使用
             */
            try {
                //等待服务端数据返回
                boolean await = count.await(1800, TimeUnit.SECONDS);
                if(!await){
                    throw new ApplicationException(StrUtil.format("超过等待时间，下载文件失败：文件路劲:{}",filePath));
                }
            } catch (InterruptedException e) {
                throw new ApplicationException(StrUtil.format("下载文件失败：文件路劲:{}",filePath),e);
            }
            //如果下载过程中出现异常，则抛异常
            if(ex.get() != null){
                throw ex.get();
            }
            return byteString.get().toByteArray();
        } finally {
            channelManager.closeChannel(channel);
        }
    }
}
