package com.platon.rosettanet.admin.grpc.channel;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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
    public Channel buildChannel(String ip,int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(ip, port)
                .usePlaintext()
                .build();
        return channel;
    }
}
