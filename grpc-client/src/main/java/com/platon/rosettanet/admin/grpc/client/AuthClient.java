package com.platon.rosettanet.admin.grpc.client;

import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.entity.CommonResp;
import com.platon.rosettanet.admin.grpc.service.AuthRpcMessage;
import com.platon.rosettanet.admin.grpc.service.AuthServiceGrpc;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import io.grpc.Channel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/7 10:24
 * @Version
 * @Desc 身份信息服务客户端
 * java服务类：AuthServiceGrpc
 * proto文件：auth_rpc_api.proto
 */

@Component
public class AuthClient {

    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;


    /**
     * 申请准入网络
     * @param identityId // 组织的身份标识Id
     * @param name               // 组织名称
     */
    public CommonResp applyIdentityJoin(String identityId,String name){
        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.拼装request
        CommonMessage.OrganizationIdentityInfo orgInfo = CommonMessage.OrganizationIdentityInfo
                .newBuilder()
                .setName(name)
                .setIdentityId(identityId)
                .build();
        AuthRpcMessage.ApplyIdentityJoinRequest joinRequest = AuthRpcMessage.ApplyIdentityJoinRequest
                .newBuilder()
                .setMember(orgInfo)
                .build();
        //3.调用rpc,获取response
        CommonMessage.SimpleResponseCode responseCode = AuthServiceGrpc.newBlockingStub(channel).applyIdentityJoin(joinRequest);
        //4.处理response
        CommonResp resp = new CommonResp();
        resp.setStatus(responseCode.getStatus());
        resp.setMsg(responseCode.getMsg());
        return resp;
    }

    /**
     * 注销准入网络
     */
    public CommonResp revokeIdentityJoin(){
        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.拼装request
        CommonMessage.EmptyGetParams request = CommonMessage.EmptyGetParams
                .newBuilder()
                .build();
        //3.调用rpc,获取response
        CommonMessage.SimpleResponseCode responseCode = AuthServiceGrpc.newBlockingStub(channel).revokeIdentityJoin(request);
        //4.处理response
        CommonResp resp = new CommonResp();
        resp.setStatus(responseCode.getStatus());
        resp.setMsg(responseCode.getMsg());
        return resp;
    }
}
