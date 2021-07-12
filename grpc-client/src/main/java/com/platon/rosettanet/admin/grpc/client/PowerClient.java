package com.platon.rosettanet.admin.grpc.client;

import com.platon.rosettanet.admin.dao.entity.LocalPowerDetails;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.service.*;
import io.grpc.Channel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/8 18:29
 * @Version
 * @Desc 算力服务客户端
 * java服务类：PowerServiceGrpc
 * proto文件：power_rpc_api.proto
 */

@Component
public class PowerClient {

    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;

    /**
     * 新增计算节点返回nodeId
     */
    public String addPowerNode(String internalIp, String externalIp, Integer internalPort, Integer externalPort){
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel("localhost", 50051);
        //2.拼装request
        YarnRpcMessage.SetJobNodeRequest joinRequest = YarnRpcMessage.SetJobNodeRequest.newBuilder()
                .setInternalIp(internalIp).setInternalPort(String.valueOf(internalPort))
                .setExternalIp(externalIp).setExternalPort(String.valueOf(externalPort))
                .build();
        //3.调用rpc,获取response
        YarnRpcMessage.SetJobNodeResponse responseCode = YarnServiceGrpc.newBlockingStub(channel).setJobNode(joinRequest);
        //4.处理response
        System.out.println("addPowerNode-返回信息：" + responseCode.getMsg());
        return responseCode.getMsg();

    }

    /**
     * 修改计算节点返回powerNodeId
     */
    public String updatePowerNode(String internalIp, String externalIp, Integer internalPort, Integer externalPort){
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel("localhost", 50051);
        //2.拼装request
        YarnRpcMessage.UpdateJobNodeRequest joinRequest = YarnRpcMessage.UpdateJobNodeRequest.newBuilder()
                .setInternalIp(internalIp).setInternalPort(String.valueOf(internalPort))
                .setExternalIp(externalIp).setExternalPort(String.valueOf(externalPort))
                .build();
        //3.调用rpc,获取response
        YarnRpcMessage.SetJobNodeResponse responseCode = YarnServiceGrpc.newBlockingStub(channel).updateJobNode(joinRequest);
        //4.处理response
        System.out.println("updatePowerNode-返回信息：" + responseCode.getMsg());
        return responseCode.getMsg();

    }

    /**
     * 根据powerNodeId删除计算节点
     */
    public String deletePowerNode(String powerNodeId){
//        //1.获取rpc连接
//        Channel channel = channelManager.buildChannel("localhost", 50051);
//        //2.拼装request
//        YarnRpcMessage.DeleteRegisteredNodeRequest joinRequest = YarnRpcMessage.DeleteRegisteredNodeRequest.newBuilder()
//                .setId(powerNodeId).build();
//        //3.调用rpc,获取response
//        YarnRpcMessage.SimpleResponseCode responseCode = YarnServiceGrpc.newBlockingStub(channel).deleteJobNode(joinRequest);
//        //4.处理response
//        System.out.println("deletePowerNode-返回信息：" + responseCode.getMsg());
//        return responseCode.getMsg();
        return null;
    }

    /**
     * 查询计算节点服务列表
     * (暂不确定入参)
     */
    public String GetJobNodeList(String identityId){
//        //1.获取rpc连接
//        Channel channel = channelManager.buildChannel("localhost", 50051);
//        //        //2.拼装request
//        YarnRpcMessage.EmptyGetParams joinRequest = YarnRpcMessage.EmptyGetParams.newBuilder()
//        .setIdentityId(identityId)
//        .build();
//        //3.调用rpc,获取response
//        YarnRpcMessage.GetRegisteredNodeListResponse responseCode = YarnServiceGrpc.newBlockingStub(channel).getJobNodeList(joinRequest);
//        //4.处理response
//        System.out.println("GetJobNodeList-返回信息：" + responseCode.getMsg());
//        return responseCode.getMsg();
        return null;
    }

    /**
     * 查看所有组织单个算力详情 (包含 任务描述)
     */
    public String GetPowerSingleDetailList(){
//        //1.获取rpc连接
//        Channel channel = channelManager.buildChannel("localhost", 50051);
//        //2.拼装request
//        PowerRpcMessage.GetPowerSingleDetailRequest joinRequest = PowerRpcMessage.GetPowerSingleDetailRequest.newBuilder()
//                .build();
//        //3.调用rpc,获取response
//        PowerRpcMessage.GetPowerSingleDetailResponse responseCode = PowerServiceGrpc.newBlockingStub(channel).getPowerSingleDetailList(joinRequest);
//        //4.处理response
//        System.out.println("getPowerSingleDetail-返回信息：" + responseCode.getMsg());
//        return responseCode.getMsg();
        return null;
    }

    /**
     * 启用算力 (发布算力)
     */
    public String publishPower(CommonMessage.OrganizationIdentityInfo owner, String jobNodeId, CommonMessage.PurePower information){
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel("localhost", 50051);
        //2.拼装request
        PowerRpcMessage.PublishPowerRequest joinRequest = PowerRpcMessage.PublishPowerRequest.newBuilder()
                .setOwner(owner).setJobNodeId(jobNodeId).setInformation(information)
                .build();
        //3.调用rpc,获取response
        PowerRpcMessage.PublishPowerResponse responseCode = PowerServiceGrpc.newBlockingStub(channel).publishPower(joinRequest);
        //4.处理response
        System.out.println("publishPower-返回信息：" + responseCode.getMsg());
        return responseCode.getMsg();
    }

    /**
     * 停用算力 (撤销算力)
     */
    public String revokePower(CommonMessage.OrganizationIdentityInfo owner, String powerId){
//        //1.获取rpc连接
//        Channel channel = channelManager.buildChannel("localhost", 50051);
//        //2.拼装request
//        PowerRpcMessage.RevokePowerRequest joinRequest = PowerRpcMessage.RevokePowerRequest.newBuilder()
//                .setOwner(owner).setPowerId(powerId)
//                .build();
//        //3.调用rpc,获取response
//        PowerRpcMessage.SimpleResponseCode responseCode = PowerServiceGrpc.newBlockingStub(channel).revokePower(joinRequest);
//        //4.处理response
//        System.out.println("revokePower-返回信息：" + responseCode.getMsg());
//        return responseCode.getMsg();
        return null;
    }




}
