package com.platon.rosettanet.admin.grpc.client;

import com.platon.rosettanet.admin.dao.entity.GlobalPower;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.service.*;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@Slf4j
public class PowerClient {

    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;

    /**
     * 新增计算节点返回nodeId
     */
    public YarnRpcMessage.YarnRegisteredPeerDetail addPowerNode(String internalIp, String externalIp, Integer internalPort, Integer externalPort){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        YarnRpcMessage.SetJobNodeResponse jobNodeResponse = null;
        try {
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.SetJobNodeRequest joinRequest = YarnRpcMessage.SetJobNodeRequest.newBuilder()
                    .setInternalIp(internalIp).setInternalPort(String.valueOf(internalPort))
                    .setExternalIp(externalIp).setExternalPort(String.valueOf(externalPort))
                    .build();
            //3.调用rpc,获取response
            jobNodeResponse  = YarnServiceGrpc.newBlockingStub(channel).setJobNode(joinRequest);
            long diffTime = System.currentTimeMillis() - startTime;
            log.info("新增计算节点, 响应时间:{}, 响应数据:{}", diffTime+"ms", jobNodeResponse.toString());
            if (jobNodeResponse.getStatus() != 0 || !GrpcConstant.ok.equals(jobNodeResponse.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        }finally {
            channelManager.closeChannel(channel);
        }
        return jobNodeResponse.getJobNode();
    }

    /**
     * 修改计算节点返回powerNodeId
     */
    public YarnRpcMessage.YarnRegisteredPeerDetail updatePowerNode(String powerNodeId, String internalIp, String externalIp, Integer internalPort, Integer externalPort){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        YarnRpcMessage.SetJobNodeResponse jobNodeResponse = null;
        try{
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.UpdateJobNodeRequest joinRequest = YarnRpcMessage.UpdateJobNodeRequest.newBuilder()
                    .setInternalIp(internalIp).setInternalPort(String.valueOf(internalPort))
                    .setExternalIp(externalIp).setExternalPort(String.valueOf(externalPort))
                    .setId(powerNodeId).build();
            //3.调用rpc,获取response
            jobNodeResponse = YarnServiceGrpc.newBlockingStub(channel).updateJobNode(joinRequest);
            //4.处理response
            if (jobNodeResponse.getStatus() != 0 || !GrpcConstant.ok.equals(jobNodeResponse.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        } finally {
            channelManager.closeChannel(channel);
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("修改计算节点, 响应时间:{}, 响应数据:{}", diffTime+"ms", jobNodeResponse.toString());
        return jobNodeResponse.getJobNode();
    }

    /**
     * 根据powerNodeId删除计算节点
     */
    public int deletePowerNode(String powerNodeId){
        long startTime = System.currentTimeMillis();
        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.拼装request
        CommonMessage.DeleteRegisteredNodeRequest joinRequest = CommonMessage.DeleteRegisteredNodeRequest.newBuilder()
                .setId(powerNodeId).build();
        //3.调用rpc,获取response
        CommonMessage.SimpleResponseCode simpleResponseCode = YarnServiceGrpc.newBlockingStub(channel).deleteJobNode(joinRequest);
        //4.处理response
        if (simpleResponseCode.getStatus() != 0 || !GrpcConstant.ok.equals(simpleResponseCode.getMsg())) {
            throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("删除计算节点, 响应时间:{}, 响应数据:{}", diffTime+"ms", simpleResponseCode.toString());
        return 1;
    }

    /**
     * 查看当前组织所有节点各个算力的详情 (包含 任务信息)
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
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            PowerRpcMessage.PublishPowerRequest joinRequest = PowerRpcMessage.PublishPowerRequest.newBuilder()
                    .setOwner(owner).setJobNodeId(jobNodeId).setInformation(information)
                    .build();
            //3.调用rpc,获取response
            PowerRpcMessage.PublishPowerResponse responseCode = PowerServiceGrpc.newBlockingStub(channel).publishPower(joinRequest);
            //4.处理response
            System.out.println("publishPower-返回信息：" + responseCode.getMsg());
            return responseCode.getMsg();
        } finally {
            channelManager.closeChannel(channel);
        }
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

    /**
     * 获取全网算力信息
     * @return
     */
    public List<GlobalPower> getPowerTotalDetailList(){
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            CommonMessage.EmptyGetParams request = CommonMessage.EmptyGetParams
                    .newBuilder()
                    .build();
            //3.调用rpc,获取response
            PowerRpcMessage.GetPowerTotalDetailListResponse response = PowerServiceGrpc.newBlockingStub(channel).getPowerTotalDetailList(request);
            //4.处理response
            List<PowerRpcMessage.GetPowerTotalDetailResponse> powerList = response.getPowerListList();
            List<GlobalPower> globalPowerList = new ArrayList<>();
            powerList.forEach(powerResponse -> {
                // 算力拥有者信息
                CommonMessage.OrganizationIdentityInfo owner = powerResponse.getOwner();
                String identityId = owner.getIdentityId();
                String orgName = owner.getName();
    //            //  总算力详情
    //            message PowerTotalDetail {
    //                ResourceUsedDetailShow information        = 1;                 // 算力实况
    //                uint32                 total_task_count   = 2;            // 算力上总共执行的任务数 (已完成的和正在执行的)
    //                uint32                 current_task_count = 3;          // 算力上正在执行的任务数
    //                repeated PowerTask     tasks              = 4;                       // 算力上正在执行的任务详情信息
    //                string                 state              = 5;                       // 算力状态 (create: 还未发布的算力; release: 已发布的算力; revoke: 已撤销的算力)
    //            }
                PowerRpcMessage.PowerTotalDetail powerDetail = powerResponse.getPower();
    //            message ResourceUsedDetailShow {
    //                uint64 total_mem       = 2;             // 服务系统的总内存 (单位: byte)
    //                uint64 used_mem        = 3;              // 服务系统的已用内存 (单位: byte)
    //                uint64 total_processor = 4;       // 服务的总内核数 (单位: 个)
    //                uint64 used_processor  = 5;        // 服务的已用内核数 (单位: 个)
    //                uint64 total_bandwidth = 6;       // 服务的总带宽数 (单位: bps)
    //                uint64 used_bandwidth  = 7;        // 服务的已用带宽数 (单位: bps)
    //            }
                CommonMessage.ResourceUsedDetailShow information = powerDetail.getInformation();// 算力实况
                GlobalPower globalPower = new GlobalPower();
                globalPower.setIdentityId(identityId);
                globalPower.setOrgName(orgName);
                globalPower.setTotalMemory(information.getTotalMem());
                globalPower.setUsedMemory(information.getUsedMem());
                globalPower.setTotalCore((int)information.getTotalProcessor());
                globalPower.setUsedCore((int)information.getUsedProcessor());
                globalPower.setTotalBandwidth(information.getTotalBandwidth());
                globalPower.setUsedBandwidth(information.getUsedBandwidth());
                globalPowerList.add(globalPower);
            });
            return globalPowerList;
        } finally {
            channelManager.closeChannel(channel);
        }
    }


}
