package com.platon.metis.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.metis.admin.common.exception.CallGrpcServiceFailed;
import com.platon.metis.admin.dao.entity.LocalPowerJoinTask;
import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.grpc.channel.SimpleChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.service.PowerRpcMessage;
import com.platon.metis.admin.grpc.service.PowerServiceGrpc;
import com.platon.metis.admin.grpc.service.YarnRpcMessage;
import com.platon.metis.admin.grpc.service.YarnServiceGrpc;
import com.platon.metis.admin.grpc.types.Resourcedata;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public YarnRpcMessage.YarnRegisteredPeerDetail addPowerNode(String internalIp, String externalIp, Integer internalPort, Integer externalPort) {
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
    /**
     * 查询计算服务列表
     */
    public List<LocalPowerNode> getLocalPowerNodeList(){
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
        return convertToLocalPowerNodeList(response.getNodesList());
    }

    private List<LocalPowerNode> convertToLocalPowerNodeList(List<YarnRpcMessage.YarnRegisteredPeer> nodeList) {
        return nodeList.parallelStream().map(node -> {
            LocalPowerNode localPowerNode = new LocalPowerNode();
            localPowerNode.setPowerNodeId(node.getNodeDetail().getId());
            localPowerNode.setPowerNodeName("PowerNode_"+ node.getNodeDetail().getInternalIp() + "_" + StringUtils.trimToEmpty(node.getNodeDetail().getInternalPort()));
            localPowerNode.setInternalIp(node.getNodeDetail().getInternalIp());
            localPowerNode.setInternalPort(StringUtils.isEmpty(node.getNodeDetail().getInternalPort()) ? null : Integer.valueOf(node.getNodeDetail().getInternalPort()));
            localPowerNode.setExternalIp(node.getNodeDetail().getExternalIp());
            localPowerNode.setExternalPort(StringUtils.isEmpty(node.getNodeDetail().getExternalPort()) ? null : Integer.valueOf(node.getNodeDetail().getExternalPort()));
            localPowerNode.setConnStatus(node.getNodeDetail().getConnState().getNumber());
            return localPowerNode;
        }).collect(Collectors.toList());
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
     * 查看当前组织各个算力的详情 (如果有任务，则包含正在执行的任务信息)
     */
    public Pair<List<LocalPowerNode>, List<LocalPowerJoinTask>> getLocalPowerNodeListAndJoinTaskList(){
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        Empty request = Empty
                .newBuilder()
                .build();
        //3.调用rpc,获取response
        PowerRpcMessage.GetLocalPowerDetailListResponse response = PowerServiceGrpc.newBlockingStub(channel).getLocalPowerDetailList(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        return convertToLocalPowerNodeAndTaskJoinedList(response.getPowerListList());
    }

    private Pair<List<LocalPowerNode>, List<LocalPowerJoinTask>> convertToLocalPowerNodeAndTaskJoinedList(List<PowerRpcMessage.GetLocalPowerDetailResponse> localPowerDetailList){

        List<LocalPowerNode> localPowerNodeList = new ArrayList<>();
        List<LocalPowerJoinTask> localPowerJoinTaskList = new ArrayList<>();

        localPowerDetailList.forEach(item->{
            // 算力实况
            Resourcedata.PowerUsageDetail powerUsageDetail = item.getPower();

            Resourcedata.ResourceUsageOverview usageOverview = powerUsageDetail.getInformation();

            // 保存计算节点算力信息开始
            LocalPowerNode localPowerNode = new LocalPowerNode();
            localPowerNode.setPowerNodeId(item.getJobNodeId());
            localPowerNode.setPowerId(item.getPowerId());
            localPowerNode.setStartTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(powerUsageDetail.getPublishAt()), ZoneOffset.UTC));
            localPowerNode.setPowerStatus(powerUsageDetail.getState().getNumber()); //更新算力状态
            localPowerNode.setMemory(usageOverview.getTotalMem());
            localPowerNode.setCore(usageOverview.getTotalProcessor());
            localPowerNode.setBandwidth(usageOverview.getTotalBandwidth());
            localPowerNode.setUsedMemory(usageOverview.getUsedMem());
            localPowerNode.setUsedCore(usageOverview.getUsedProcessor());
            localPowerNode.setUsedBandwidth(usageOverview.getUsedBandwidth());
            localPowerNode.setUpdateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(powerUsageDetail.getUpdateAt()), ZoneOffset.UTC));
            localPowerNodeList.add(localPowerNode);

            List<Resourcedata.PowerTask> powerTaskList = powerUsageDetail.getTasksList();
            localPowerJoinTaskList.addAll(powerTaskList.parallelStream().map(powerTask->{
                LocalPowerJoinTask localPowerJoinTask = new LocalPowerJoinTask();
                localPowerJoinTask.setPowerNodeId(item.getJobNodeId());
                localPowerJoinTask.setTaskId(powerTask.getTaskId());
                localPowerJoinTask.setTaskName(powerTask.getTaskName());
                localPowerJoinTask.setOwnerIdentityId(powerTask.getOwner().getIdentityId());
                localPowerJoinTask.setOwnerIdentityName(powerTask.getOwner().getNodeName());
                localPowerJoinTask.setTaskStartTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(powerTask.getCreateAt()), ZoneOffset.UTC));  // 发起时间
                localPowerJoinTask.setUsedMemory(powerTask.getOperationSpend().getMemory());// 已使用内存
                localPowerJoinTask.setUsedCore(powerTask.getOperationSpend().getProcessor()); // 已使用核数
                localPowerJoinTask.setUsedBandwidth(powerTask.getOperationSpend().getBandwidth());// 已使用带宽
                return localPowerJoinTask;
            }).collect(Collectors.toList()));


        });
        return new ImmutablePair<>(localPowerNodeList, localPowerJoinTaskList);
    }


}