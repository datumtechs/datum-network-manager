package com.platon.rosettanet.admin.grpc.service;

import com.platon.rosettanet.admin.grpc.AuthRpcMessage;
import com.platon.rosettanet.admin.grpc.CommonMessage;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Test;


/**
 * @Author liushuyu
 * @Date 2021/7/6 19:50
 * @Version
 * @Desc
 */
public class AuthClientTest {


    @Test
    public void test() {
        Channel channel =  ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

        AuthRpcMessage.ApplyIdentityJoinRequest joinRequest = AuthRpcMessage.ApplyIdentityJoinRequest.newBuilder().build();
        CommonMessage.SimpleResponseCode responseCode = AuthServiceGrpc.newBlockingStub(channel).applyIdentityJoin(joinRequest);
        System.out.println("###############" + responseCode.getMsg());
        System.out.println("111111111");
    }
}
