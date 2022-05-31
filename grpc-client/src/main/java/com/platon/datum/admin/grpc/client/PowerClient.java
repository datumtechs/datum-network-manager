package com.platon.datum.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.entity.LocalPowerJoinTask;
import com.platon.datum.admin.dao.entity.LocalPowerNode;
import com.platon.datum.admin.grpc.carrier.api.PowerRpcApi;
import com.platon.datum.admin.grpc.carrier.api.PowerServiceGrpc;
import com.platon.datum.admin.grpc.carrier.api.SysRpcApi;
import com.platon.datum.admin.grpc.carrier.api.YarnServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import com.platon.datum.admin.grpc.carrier.types.ResourceData;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public SysRpcApi.YarnRegisteredPeerDetail addPowerNode(String internalIp, String externalIp, Integer internalPort, Integer externalPort) {
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        SysRpcApi.SetJobNodeRequest joinRequest = SysRpcApi.SetJobNodeRequest.newBuilder()
                .setInternalIp(internalIp)
                .setInternalPort(String.valueOf(internalPort))
                .setExternalIp(externalIp)
                .setExternalPort(String.valueOf(externalPort))
                .build();
        //3.调用rpc,获取response
        SysRpcApi.SetJobNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).setJobNode(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getNode();
    }

    /**
     * 修改计算节点返回powerNodeId
     */
    public SysRpcApi.YarnRegisteredPeerDetail updatePowerNode(String powerNodeId, String internalIp, String externalIp, Integer internalPort, Integer externalPort) {
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        SysRpcApi.UpdateJobNodeRequest joinRequest = SysRpcApi.UpdateJobNodeRequest.newBuilder()
                .setInternalIp(internalIp)
                .setInternalPort(String.valueOf(internalPort))
                .setExternalIp(externalIp)
                .setExternalPort(String.valueOf(externalPort))
                .setId(powerNodeId)
                .build();
        //3.调用rpc,获取response
        SysRpcApi.SetJobNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).updateJobNode(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getNode();
    }

    /**
     * 根据powerNodeId删除计算节点
     */
    public void deletePowerNode(String powerNodeId) {
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        SysRpcApi.DeleteRegisteredNodeRequest joinRequest = SysRpcApi.DeleteRegisteredNodeRequest.newBuilder()
                .setId(powerNodeId).build();
        //3.调用rpc,获取response
        Common.SimpleResponse response = YarnServiceGrpc.newBlockingStub(channel).deleteJobNode(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 查询计算服务列表
     */
    /**
     * 查询计算服务列表
     */
    public List<LocalPowerNode> getLocalPowerNodeList() {

        log.debug("从carrier查询本组织的计算节点列表");

        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        Empty jobNodeListRequest = Empty.newBuilder().build();
        //3.调用rpc,获取response
        SysRpcApi.GetRegisteredNodeListResponse response = YarnServiceGrpc.newBlockingStub(channel).getJobNodeList(jobNodeListRequest);

        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        log.debug("从carrier查询本组织的计算节点列表, 数量：{}", response.getNodesList().size());
        return convertToLocalPowerNodeList(response.getNodesList());
    }

    private List<LocalPowerNode> convertToLocalPowerNodeList(List<SysRpcApi.YarnRegisteredPeer> nodeList) {
        return nodeList.parallelStream().map(node -> {
            LocalPowerNode localPowerNode = new LocalPowerNode();
            localPowerNode.setNodeId(node.getNodeDetail().getId());
            localPowerNode.setNodeName("PowerNode_" + node.getNodeDetail().getInternalIp() + "_" + StringUtils.trimToEmpty(node.getNodeDetail().getInternalPort()));
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
        PowerRpcApi.PublishPowerRequest joinRequest = PowerRpcApi.PublishPowerRequest.newBuilder()
                .setJobNodeId(jobNodeId).build();
        //3.调用rpc,获取response
        PowerRpcApi.PublishPowerResponse response = PowerServiceGrpc.newBlockingStub(channel).publishPower(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
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
        PowerRpcApi.RevokePowerRequest joinRequest = PowerRpcApi.RevokePowerRequest.newBuilder()
                .setPowerId(powerId)
                .build();
        //3.调用rpc,获取response
        Common.SimpleResponse response = PowerServiceGrpc.newBlockingStub(channel).revokePower(joinRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 查看当前组织各个算力的详情 (如果有任务，则包含正在执行的任务信息)
     */
    public Pair<List<LocalPowerNode>, List<LocalPowerJoinTask>> getLocalPowerNodeListAndJoinTaskList() {
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        Empty request = Empty
                .newBuilder()
                .build();
        //3.调用rpc,获取response
        PowerRpcApi.GetLocalPowerDetailListResponse response = PowerServiceGrpc.newBlockingStub(channel).getLocalPowerDetailList(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        return convertToLocalPowerNodeAndTaskJoinedList(response.getPowersList());
    }

    private Pair<List<LocalPowerNode>, List<LocalPowerJoinTask>> convertToLocalPowerNodeAndTaskJoinedList(List<PowerRpcApi.GetLocalPowerDetail> localPowerDetailList) {
        List<LocalPowerNode> localPowerNodeList = new ArrayList<>();
        List<LocalPowerJoinTask> localPowerJoinTaskList = new ArrayList<>();
        localPowerDetailList.forEach(item -> {
            // 算力实况
            ResourceData.PowerUsageDetail powerUsageDetail = item.getPower();

            ResourceData.ResourceUsageOverview usageOverview = powerUsageDetail.getInformation();

            // 保存计算节点算力信息开始
            LocalPowerNode localPowerNode = toLocalPowerNode(item, powerUsageDetail, usageOverview);
            localPowerNodeList.add(localPowerNode);

            List<ResourceData.PowerTask> powerTaskList = powerUsageDetail.getTasksList();
            localPowerJoinTaskList.addAll(
                    powerTaskList.stream()
                            .map(powerTask -> toLocalPowerJoinTask(item, powerTask))
                            .collect(Collectors.toList()));
        });
        return new ImmutablePair<>(localPowerNodeList, localPowerJoinTaskList);
    }

    private LocalPowerNode toLocalPowerNode(PowerRpcApi.GetLocalPowerDetail item,
                                            ResourceData.PowerUsageDetail powerUsageDetail,
                                            ResourceData.ResourceUsageOverview usageOverview) {
        LocalPowerNode localPowerNode = new LocalPowerNode();
        localPowerNode.setNodeId(item.getJobNodeId());
        localPowerNode.setPowerId(item.getPowerId());
        //localPowerNode.setStartTime(LocalDateTimeUtil.getLocalDateTime(powerUsageDetail.getPublishAt()), ZoneOffset.UTC));
        localPowerNode.setPowerStatus(powerUsageDetail.getStateValue()); //更新算力状态
        localPowerNode.setMemory(usageOverview.getTotalMem());
        localPowerNode.setCore(usageOverview.getTotalProcessor());
        localPowerNode.setBandwidth(usageOverview.getTotalBandwidth());
        localPowerNode.setUsedMemory(usageOverview.getUsedMem());
        localPowerNode.setUsedCore(usageOverview.getUsedProcessor());
        localPowerNode.setUsedBandwidth(usageOverview.getUsedBandwidth());
        //localPowerNode.setUpdateTime(LocalDateTimeUtil.getLocalDateTime(powerUsageDetail.getUpdateAt()), ZoneOffset.UTC));
        return localPowerNode;
    }

    private LocalPowerJoinTask toLocalPowerJoinTask(PowerRpcApi.GetLocalPowerDetail item, ResourceData.PowerTask powerTask) {
        LocalPowerJoinTask localPowerJoinTask = new LocalPowerJoinTask();
        localPowerJoinTask.setNodeId(item.getJobNodeId());
        localPowerJoinTask.setTaskId(powerTask.getTaskId());
        localPowerJoinTask.setTaskName(powerTask.getTaskName());
        localPowerJoinTask.setOwnerIdentityId(powerTask.getOwner().getIdentityId());
        localPowerJoinTask.setOwnerIdentityName(powerTask.getOwner().getNodeName());
        localPowerJoinTask.setTaskStartTime(LocalDateTimeUtil.getLocalDateTime(powerTask.getCreateAt()));  // 发起时间
        localPowerJoinTask.setUsedMemory(powerTask.getOperationSpend().getMemory());// 已使用内存
        localPowerJoinTask.setUsedCore(powerTask.getOperationSpend().getProcessor()); // 已使用核数
        localPowerJoinTask.setUsedBandwidth(powerTask.getOperationSpend().getBandwidth());// 已使用带宽
        return localPowerJoinTask;
    }
}