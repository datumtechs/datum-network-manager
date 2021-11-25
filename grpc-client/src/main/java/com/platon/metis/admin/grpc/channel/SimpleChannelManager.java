package com.platon.metis.admin.grpc.channel;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

/**
 * @Author liushuyu
 * @Date 2021/7/7 10:58
 * @Version
 * @Desc
 */

@Component
public class SimpleChannelManager extends BaseChannelManager{


    @Override
    public ManagedChannel buildChannel(String ip,int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(ip, port)
                .usePlaintext()
                .keepAliveWithoutCalls(true)
                .build();
        return channel;
    }


    @SneakyThrows
    public void closeChannel(Channel channel){
//        if(channel == null){
//            return;
//        }
//        if(channel instanceof ManagedChannel){
//            ManagedChannel managedChannel = (ManagedChannel)channel;
//            if(managedChannel.isTerminated()){
//                return;
//            }
//            managedChannel.resetConnectBackoff();
//            managedChannel.shutdown();
//            managedChannel.awaitTermination(10, TimeUnit.SECONDS);
//            managedChannel.shutdownNow();
//        }
    }
}
