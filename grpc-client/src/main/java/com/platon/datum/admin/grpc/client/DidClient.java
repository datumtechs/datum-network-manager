package com.platon.datum.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.grpc.carrier.api.DIDServiceGrpc;
import com.platon.datum.admin.grpc.carrier.api.DidRpcApi;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2022/7/28 18:32
 * @Version
 * @Desc
 */

@Slf4j
@Component
public class DidClient {

    @Resource
    private SimpleChannelManager channelManager;


    /**
     * 申请did
     */
    public String createDID(String scheduleIP, int schedulePort) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.buildChannel(scheduleIP, schedulePort);
        //2.拼装request
        //3.调用rpc,获取response
        DidRpcApi.CreateDIDResponse response = DIDServiceGrpc.newBlockingStub(channel).createDID(Empty.newBuilder().build());
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getDid();
    }

    /**
     * 向委员会成员申请证书
     *
     * @param ip         委员会成员调度服务ip
     * @param port       委员会成员调度服务端口
     * @param identityId 组织的身份标识Id
     * @param pctId      组织名称
     * @param claim      证书材料
     */
    public String createVC(String ip, int port, String identityId, int pctId, String claim) {
        log.debug("从carrier申请入网，identityId:{}", identityId);
        //1.获取rpc连接
        ManagedChannel channel = channelManager.buildChannel(ip, port);
        //2.拼装request
        DidRpcApi.CreateVCRequest request = DidRpcApi.CreateVCRequest.newBuilder()
                .setDid(identityId)
                .setPctId(pctId)
                .setClaim(claim)
                .build();

        //3.调用rpc,获取response
        DidRpcApi.CreateVCResponse response = DIDServiceGrpc.newBlockingStub(channel).createVC(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getVc();
    }

}
