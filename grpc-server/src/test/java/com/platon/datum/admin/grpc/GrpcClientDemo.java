package com.platon.datum.admin.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@Slf4j
public class GrpcClientDemo {


    @Test
    public void listPowerSummary() {
        log.info("start to test listPowerSummary()...");

        //普通的grpc需要使用明文，所以usePlaintext参数，表示明文进行请求。
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("test.technocore.network",88)
//                .usePlaintext()
//                .build();
        //grpcs需要使用密文，所以要去掉usePlaintext
        /**
         */
        ManagedChannel channel = ManagedChannelBuilder.forAddress("test.technocore.network", 880)
//                .usePlaintext()
                .build();

//        com.google.protobuf.Empty request = com.google.protobuf.Empty.newBuilder().build();
//        ResourceServiceGrpc.ResourceServiceBlockingStub resourceServiceBlockingStub = ResourceServiceGrpc.newBlockingStub(channel);
//        Resource.ListPowerSummaryResponse response = resourceServiceBlockingStub.listPowerSummary(request);
//
//        log.info("listPowerSummary(), response:{}", response);
    }

}
