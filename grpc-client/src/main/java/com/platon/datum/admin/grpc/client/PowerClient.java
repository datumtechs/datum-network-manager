package com.platon.datum.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.entity.PowerJoinTask;
import com.platon.datum.admin.dao.entity.PowerNode;
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
import java.util.concurrent.TimeUnit;
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
        log.debug("addPowerNode,request:{}",joinRequest);
        //3.调用rpc,获取response
        SysRpcApi.SetJobNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).withDeadlineAfter(30, TimeUnit.SECONDS).setJobNode(joinRequest);
        log.debug("addPowerNode,response:{}",response);
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
        log.debug("updatePowerNode,request:{}",joinRequest);
        //3.调用rpc,获取response
        SysRpcApi.SetJobNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).withDeadlineAfter(30, TimeUnit.SECONDS).updateJobNode(joinRequest);
        log.debug("updatePowerNode,response:{}",response);
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
        log.debug("deletePowerNode,request:{}",joinRequest);
        //3.调用rpc,获取response
        Common.SimpleResponse response = YarnServiceGrpc.newBlockingStub(channel).withDeadlineAfter(30, TimeUnit.SECONDS).deleteJobNode(joinRequest);
        log.debug("deletePowerNode,response:{}",response);
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
    public List<PowerNode> getLocalPowerNodeList() {
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        Empty jobNodeListRequest = Empty.newBuilder().build();
        log.debug("getLocalPowerNodeList,request:{}",jobNodeListRequest);
        //3.调用rpc,获取response
        SysRpcApi.GetRegisteredNodeListResponse response = YarnServiceGrpc.newBlockingStub(channel).withDeadlineAfter(30, TimeUnit.SECONDS).getJobNodeList(jobNodeListRequest);
        log.debug("getLocalPowerNodeList,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return convertToLocalPowerNodeList(response.getNodesList());
    }

    private List<PowerNode> convertToLocalPowerNodeList(List<SysRpcApi.YarnRegisteredPeer> nodeList) {
        return nodeList.parallelStream().map(node -> {
            PowerNode powerNode = new PowerNode();
            powerNode.setNodeId(node.getNodeDetail().getId());
            powerNode.setNodeName("PowerNode_" + node.getNodeDetail().getInternalIp() + "_" + StringUtils.trimToEmpty(node.getNodeDetail().getInternalPort()));
            powerNode.setInternalIp(node.getNodeDetail().getInternalIp());
            powerNode.setInternalPort(StringUtils.isEmpty(node.getNodeDetail().getInternalPort()) ? null : Integer.valueOf(node.getNodeDetail().getInternalPort()));
            powerNode.setExternalIp(node.getNodeDetail().getExternalIp());
            powerNode.setExternalPort(StringUtils.isEmpty(node.getNodeDetail().getExternalPort()) ? null : Integer.valueOf(node.getNodeDetail().getExternalPort()));
            powerNode.setConnStatus(node.getNodeDetail().getConnState().getNumber());
            return powerNode;
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
        log.debug("publishPower,request:{}",joinRequest);
        //3.调用rpc,获取response
        PowerRpcApi.PublishPowerResponse response = PowerServiceGrpc.newBlockingStub(channel).withDeadlineAfter(30, TimeUnit.SECONDS).publishPower(joinRequest);
        log.debug("publishPower,response:{}",response);
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
        log.debug("revokePower,request:{}",joinRequest);
        //3.调用rpc,获取response
        Common.SimpleResponse response = PowerServiceGrpc.newBlockingStub(channel).withDeadlineAfter(30, TimeUnit.SECONDS).revokePower(joinRequest);
        log.debug("revokePower,response:{}",response);
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
    public Pair<List<PowerNode>, List<PowerJoinTask>> getLocalPowerNodeListAndJoinTaskList() {
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        Empty request = Empty
                .newBuilder()
                .build();
        log.debug("getLocalPowerNodeListAndJoinTaskList,request:{}",request);
        //3.调用rpc,获取response
        PowerRpcApi.GetLocalPowerDetailListResponse response = PowerServiceGrpc.newBlockingStub(channel).withDeadlineAfter(30, TimeUnit.SECONDS).getLocalPowerDetailList(request);
        log.debug("getLocalPowerNodeListAndJoinTaskList,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return convertToLocalPowerNodeAndTaskJoinedList(response.getPowersList());
    }

    private Pair<List<PowerNode>, List<PowerJoinTask>> convertToLocalPowerNodeAndTaskJoinedList(List<PowerRpcApi.GetLocalPowerDetail> localPowerDetailList) {
        List<PowerNode> powerNodeList = new ArrayList<>();
        List<PowerJoinTask> powerJoinTaskList = new ArrayList<>();
        localPowerDetailList.forEach(item -> {
            // 算力实况
            ResourceData.PowerUsageDetail powerUsageDetail = item.getPower();

            ResourceData.ResourceUsageOverview usageOverview = powerUsageDetail.getInformation();

            // 保存计算节点算力信息开始
            PowerNode powerNode = toLocalPowerNode(item, powerUsageDetail, usageOverview);
            powerNodeList.add(powerNode);

            List<ResourceData.PowerTask> powerTaskList = powerUsageDetail.getTasksList();
            powerJoinTaskList.addAll(
                    powerTaskList.stream()
                            .map(powerTask -> toLocalPowerJoinTask(item, powerTask))
                            .collect(Collectors.toList()));
        });
        return new ImmutablePair<>(powerNodeList, powerJoinTaskList);
    }

    private PowerNode toLocalPowerNode(PowerRpcApi.GetLocalPowerDetail item,
                                       ResourceData.PowerUsageDetail powerUsageDetail,
                                       ResourceData.ResourceUsageOverview usageOverview) {
        PowerNode powerNode = new PowerNode();
        powerNode.setNodeId(item.getJobNodeId());
        powerNode.setPowerId(item.getPowerId());
        //powerNode.setStartTime(LocalDateTimeUtil.getLocalDateTime(powerUsageDetail.getPublishAt()), ZoneOffset.UTC));
        powerNode.setPowerStatus(powerUsageDetail.getStateValue()); //更新算力状态
        powerNode.setMemory(usageOverview.getTotalMem());
        powerNode.setCore(usageOverview.getTotalProcessor());
        powerNode.setBandwidth(usageOverview.getTotalBandwidth());
        powerNode.setUsedMemory(usageOverview.getUsedMem());
        powerNode.setUsedCore(usageOverview.getUsedProcessor());
        powerNode.setUsedBandwidth(usageOverview.getUsedBandwidth());
        //powerNode.setUpdateTime(LocalDateTimeUtil.getLocalDateTime(powerUsageDetail.getUpdateAt()), ZoneOffset.UTC));
        return powerNode;
    }

    private PowerJoinTask toLocalPowerJoinTask(PowerRpcApi.GetLocalPowerDetail item, ResourceData.PowerTask powerTask) {
        PowerJoinTask powerJoinTask = new PowerJoinTask();
        powerJoinTask.setNodeId(item.getJobNodeId());
        powerJoinTask.setTaskId(powerTask.getTaskId());
        powerJoinTask.setTaskName(powerTask.getTaskName());
        powerJoinTask.setOwnerIdentityId(powerTask.getOwner().getIdentityId());
        powerJoinTask.setOwnerIdentityName(powerTask.getOwner().getNodeName());
        powerJoinTask.setTaskStartTime(LocalDateTimeUtil.getLocalDateTime(powerTask.getCreateAt()));  // 发起时间
        powerJoinTask.setUsedMemory(powerTask.getOperationSpend().getMemory());// 已使用内存
        powerJoinTask.setUsedCore(powerTask.getOperationSpend().getProcessor()); // 已使用核数
        powerJoinTask.setUsedBandwidth(powerTask.getOperationSpend().getBandwidth());// 已使用带宽
        return powerJoinTask;
    }
}