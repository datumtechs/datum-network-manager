package com.platon.metis.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.metis.admin.common.exception.CallGrpcServiceFailed;
import com.platon.metis.admin.dao.entity.GlobalPower;
import com.platon.metis.admin.grpc.channel.SimpleChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.service.PowerRpcMessage;
import com.platon.metis.admin.grpc.service.PowerServiceGrpc;
import com.platon.metis.admin.grpc.service.YarnRpcMessage;
import com.platon.metis.admin.grpc.service.YarnServiceGrpc;
import com.platon.metis.admin.grpc.types.Resourcedata;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static com.platon.metis.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

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

    @Resource
    private SimpleChannelManager channelManager;

    /**
     * 新增计算节点返回nodeId
     */
    public YarnRpcMessage.YarnRegisteredPeerDetail addPowerNode(String internalIp, String externalIp, Integer internalPort, Integer externalPort) throws Exception{
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        YarnRpcMessage.SetJobNodeRequest joinRequest = YarnRpcMessage.SetJobNodeRequest.newBuilder()
                .setInternalIp(internalIp).setInternalPort(String.valueOf(internalPort))
                .setExternalIp(externalIp).setExternalPort(String.valueOf(externalPort))
                .build();
        //3.调用rpc,获取response
        YarnRpcMessage.SetJobNodeResponse response  = YarnServiceGrpc.newBlockingStub(channel).setJobNode(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getNode();
    }

    /**
     * 修改计算节点返回powerNodeId
     */
    public YarnRpcMessage.YarnRegisteredPeerDetail updatePowerNode(String powerNodeId, String internalIp, String externalIp, Integer internalPort, Integer externalPort) throws Exception{
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        YarnRpcMessage.UpdateJobNodeRequest joinRequest = YarnRpcMessage.UpdateJobNodeRequest.newBuilder()
                .setInternalIp(internalIp).setInternalPort(String.valueOf(internalPort))
                .setExternalIp(externalIp).setExternalPort(String.valueOf(externalPort))
                .setId(powerNodeId).build();
        //3.调用rpc,获取response
        YarnRpcMessage.SetJobNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).updateJobNode(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getNode();
    }

    /**
     * 根据powerNodeId删除计算节点
     */
    public void deletePowerNode(String powerNodeId) throws Exception {
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        YarnRpcMessage.DeleteRegisteredNodeRequest joinRequest = YarnRpcMessage.DeleteRegisteredNodeRequest.newBuilder()
                .setId(powerNodeId).build();
        //3.调用rpc,获取response
        CommonBase.SimpleResponse response = YarnServiceGrpc.newBlockingStub(channel).deleteJobNode(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 查询计算服务列表
     */
    public List<YarnRpcMessage.YarnRegisteredPeer> getJobNodeList() throws Exception {
        Channel channel = channelManager.getCarrierChannel();
            //2.拼装request
        Empty jobNodeListRequest = Empty.newBuilder().build();
            //3.调用rpc,获取response
        YarnRpcMessage.GetRegisteredNodeListResponse response = YarnServiceGrpc.newBlockingStub(channel).getJobNodeList(jobNodeListRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getNodesList();
    }

    /**
     * 启用算力 (发布算力)
     */
    public String publishPower(String jobNodeId) {
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        PowerRpcMessage.PublishPowerRequest joinRequest = PowerRpcMessage.PublishPowerRequest.newBuilder()
              .setJobNodeId(jobNodeId).build();
        //3.调用rpc,获取response
        PowerRpcMessage.PublishPowerResponse response = PowerServiceGrpc.newBlockingStub(channel).publishPower(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getPowerId();
    }

    /**
     * 停用算力 (撤销算力)
     */
    public void revokePower(String powerId) {
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        PowerRpcMessage.RevokePowerRequest joinRequest = PowerRpcMessage.RevokePowerRequest.newBuilder()
                .setPowerId(powerId)
                .build();
        //3.调用rpc,获取response
        CommonBase.SimpleResponse response = PowerServiceGrpc.newBlockingStub(channel).revokePower(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 查看当前组织各个算力的详情 (包含 任务信息)
     */
    public List<PowerRpcMessage.GetLocalPowerDetailResponse> getLocalPowerDetailList(){
        Channel channel = channelManager.getCarrierChannel();


        //2.拼装request
        Empty emptyGetParams = Empty.newBuilder().build();
        //3.调用rpc,获取response
        PowerRpcMessage.GetLocalPowerDetailListResponse response = PowerServiceGrpc.newBlockingStub(channel).getLocalPowerDetailList(emptyGetParams);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getPowerListList();
    }

    /**
     * 获取全网算力信息
     * @return
     */
    public List<GlobalPower> getGlobalPowerDetailList() {
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        com.google.protobuf.Empty request = com.google.protobuf.Empty
                .newBuilder()
                .build();
        //3.调用rpc,获取response
        PowerRpcMessage.GetGlobalPowerDetailListResponse response = PowerServiceGrpc.newBlockingStub(channel).getGlobalPowerDetailList(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        List<PowerRpcMessage.GetGlobalPowerDetailResponse> powerList = response.getPowerListList();
        List<GlobalPower> globalPowerList = new ArrayList<>();
        powerList.forEach(powerResponse -> {
            // 算力拥有者信息
            CommonBase.Organization owner = powerResponse.getOwner();
            String identityId = owner.getIdentityId();
            String orgName = owner.getNodeName();
            //            //  总算力详情
            //            message PowerTotalDetail {
            //                ResourceUsedDetailShow information        = 1;                 // 算力实况
            //                uint32                 total_task_count   = 2;            // 算力上总共执行的任务数 (已完成的和正在执行的)
            //                uint32                 current_task_count = 3;          // 算力上正在执行的任务数
            //                repeated PowerTask     tasks              = 4;                       // 算力上正在执行的任务详情信息
            //                string                 state              = 5;                       // 算力状态 (create: 还未发布的算力; release: 已发布的算力; revoke: 已撤销的算力)
            //            }
            Resourcedata.PowerUsageDetail powerDetail = powerResponse.getPower();
            //            message ResourceUsedDetailShow {
            //                uint64 total_mem       = 2;             // 服务系统的总内存 (单位: byte)
            //                uint64 used_mem        = 3;              // 服务系统的已用内存 (单位: byte)
            //                uint64 total_processor = 4;       // 服务的总内核数 (单位: 个)
            //                uint64 used_processor  = 5;        // 服务的已用内核数 (单位: 个)
            //                uint64 total_bandwidth = 6;       // 服务的总带宽数 (单位: bps)
            //                uint64 used_bandwidth  = 7;        // 服务的已用带宽数 (单位: bps)
            //            }
            Resourcedata.ResourceUsageOverview information = powerDetail.getInformation();// 算力实况
            GlobalPower globalPower = new GlobalPower();
            globalPower.setId(powerResponse.getPowerId());
            globalPower.setIdentityId(identityId);
            globalPower.setOrgName(powerResponse.getOwner().getNodeName());
            globalPower.setCore(information.getTotalProcessor());
            globalPower.setMemory(information.getTotalMem());
            globalPower.setBandwidth(information.getTotalBandwidth());
            globalPower.setUsedCore((int) information.getUsedProcessor());
            globalPower.setUsedMemory(information.getUsedMem());
            globalPower.setUsedBandwidth(information.getUsedBandwidth());
            globalPower.setStatus(powerDetail.getState().getNumber());
            globalPower.setPublished(powerDetail.getState().getNumber() == 2 || powerDetail.getState().getNumber() == 3);
            globalPower.setPublishAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(powerDetail.getPublishAt()), ZoneOffset.UTC));
            globalPower.setUpdateAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(powerDetail.getUpdateAt()), ZoneOffset.UTC));
            globalPowerList.add(globalPower);
        });
        return globalPowerList;

    }


}
