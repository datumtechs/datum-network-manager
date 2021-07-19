package com.platon.rosettanet.admin.grpc.channel;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author liushuyu
 * @Date 2021/7/7 10:58
 * @Version
 * @Desc
 */

@Component
public class SimpleChannelManager extends BaseChannelManager{


    @Override
    public Channel buildChannel(String ip,int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(ip, port)
                .usePlaintext()
                .build();
        return channel;
    }


    @SneakyThrows
    public void closeChannel(Channel channel){
        if(channel == null){
            return;
        }
        if(channel instanceof ManagedChannel){
            ManagedChannel managedChannel = (ManagedChannel)channel;
            if(managedChannel.isTerminated()){
                return;
            }
            managedChannel.shutdown();
            managedChannel.awaitTermination(10, TimeUnit.SECONDS);
            managedChannel.shutdownNow();
        }
    }
}
