package com.platon.metis.admin.grpc.channel;

import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.common.exception.CannotConnectGrpcServer;
import com.platon.metis.admin.common.exception.CarrierNotConfigured;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.metis.admin.grpc.interceptor.TimeoutInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author liushuyu
 * @Date 2021/7/7 10:58
 * @Version
 * @Desc
 */

@Component
@Slf4j
public class SimpleChannelManager{
    @Resource
    private TimeoutInterceptor timeoutInterceptor;

    private ManagedChannel carrierChannel;

    public ManagedChannel buildChannel(String ip,int port) throws CannotConnectGrpcServer {
        try {
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(ip, port)
                    .usePlaintext()
                    .keepAliveWithoutCalls(true)
                    .intercept(timeoutInterceptor)
                    .build();

            return channel;
        }catch (Throwable e){
            log.error("failed to connect to gRPC server {}:{}", ip, port);
            throw new CannotConnectGrpcServer();
        }
    }


     public void closeChannel(ManagedChannel managedChannel) {
        if(managedChannel == null){
            return;
        }
        if (!managedChannel.isShutdown()) {
            try {
                managedChannel.shutdown();
                if (!managedChannel.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.warn("Timed out gracefully shutting down connection: {}. ", managedChannel);
                }
            } catch (Exception e) {
                log.error("Unexpected exception while waiting for channel termination", e);
            }
        }

        // Forceful shut down if still not terminated.
        if (!managedChannel.isTerminated()) {
            try {
                managedChannel.shutdownNow();
                if (!managedChannel.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.warn("Timed out forcefully shutting down connection: {}. ", managedChannel);
                }
            } catch (Exception e) {
                log.error("Unexpected exception while waiting for channel termination", e);
            }
        }
    }

    /**
     * 获取调度服务连接
     * carrier_conn_Status = 'enabled' and carrier_status = 'enabled'
     * @throws ApplicationException
     * @return
     */
    public ManagedChannel getCarrierChannel() throws CarrierNotConfigured, CannotConnectGrpcServer {
        //获取调度服务的信息
        LocalOrg localOrgInfo = (LocalOrg) LocalOrgCache.getLocalOrgInfo();
        if(!CarrierConnStatusEnum.ENABLED.getStatus().equals(localOrgInfo.getCarrierConnStatus())){
            throw new CarrierNotConfigured();
        }
        if (carrierChannel==null){
            carrierChannel = buildChannel(localOrgInfo.getCarrierIp(), localOrgInfo.getCarrierPort());
        }

        return carrierChannel;
    }
}
