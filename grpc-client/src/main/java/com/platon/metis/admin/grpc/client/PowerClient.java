package com.platon.metis.admin.grpc.client;

import cn.hutool.core.date.DateUtil;
import com.google.protobuf.Empty;
import com.platon.metis.admin.dao.entity.GlobalPower;
import com.platon.metis.admin.dao.entity.LocalPowerJoinTask;
import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.grpc.channel.BaseChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
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
import java.util.Objects;
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

    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;

    /**
     * 新增计算节点返回nodeId
     */
    public YarnRpcMessage.YarnRegisteredPeerDetail addPowerNode(String internalIp, String externalIp, Integer internalPort, Integer externalPort){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        YarnRpcMessage.SetJobNodeResponse jobNodeResponse;
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
            //4.处理response
            if (jobNodeResponse.getStatus() != 0 || !GrpcConstant.ok.equals(jobNodeResponse.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        }finally {
            channelManager.closeChannel(channel);
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("新增计算节点, 响应时间:{}, 响应数据:{}", diffTime+"ms", jobNodeResponse.toString());
        return jobNodeResponse.getNode();
    }

    /**
     * 修改计算节点返回powerNodeId
     */
    public YarnRpcMessage.YarnRegisteredPeerDetail updatePowerNode(String powerNodeId, String internalIp, String externalIp, Integer internalPort, Integer externalPort){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        YarnRpcMessage.SetJobNodeResponse jobNodeResponse;
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
        return jobNodeResponse.getNode();
    }

    /**
     * 根据powerNodeId删除计算节点
     */
    public void deletePowerNode(String powerNodeId){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        CommonBase.SimpleResponse simpleResponseCode;
        try{
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.DeleteRegisteredNodeRequest joinRequest = YarnRpcMessage.DeleteRegisteredNodeRequest.newBuilder()
                    .setId(powerNodeId).build();
            //3.调用rpc,获取response
            simpleResponseCode = YarnServiceGrpc.newBlockingStub(channel).deleteJobNode(joinRequest);
            //4.处理response
            if (simpleResponseCode.getStatus() != 0 || !GrpcConstant.ok.equals(simpleResponseCode.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        } finally {
            channelManager.closeChannel(channel);
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("删除计算节点, 响应时间:{}, 响应数据:{}", diffTime+"ms", simpleResponseCode);
    }

    /**
     * 查询计算服务列表
     */
    public List<LocalPowerNode> getLocalPowerNodeList(){
        Channel channel = null;
        try{
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            Empty jobNodeListRequest = Empty.newBuilder().build();
            //3.调用rpc,获取response
            YarnRpcMessage.GetRegisteredNodeListResponse response = YarnServiceGrpc.newBlockingStub(channel).getJobNodeList(jobNodeListRequest);
            //4.处理response
            if(!Objects.isNull(response) && GrpcConstant.GRPC_SUCCESS_CODE == response.getStatus()){
                return convertToLocalPowerNodeList(response.getNodesList());
            }else{
                log.error("同步本地算力节点出错, code:{}, errorMsg:{}", response.getStatus(), response.getMsg());
                return null;
            }
        } finally {
            channelManager.closeChannel(channel);
        }
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
    public String publishPower(String jobNodeId){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        PowerRpcMessage.PublishPowerResponse publishPowerResponse;
        try{
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            PowerRpcMessage.PublishPowerRequest joinRequest = PowerRpcMessage.PublishPowerRequest.newBuilder()
                  .setJobNodeId(jobNodeId).build();
            //3.调用rpc,获取response
            publishPowerResponse = PowerServiceGrpc.newBlockingStub(channel).publishPower(joinRequest);
            //4.处理response
            if (publishPowerResponse.getStatus() != 0 || !GrpcConstant.ok.equals(publishPowerResponse.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        } finally {
            channelManager.closeChannel(channel);
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("启用算力接口, 响应时间:{}, 响应数据:{}", diffTime+"ms", publishPowerResponse.toString());
        return publishPowerResponse.getPowerId();
    }

    /**
     * 停用算力 (撤销算力)
     */
    public void revokePower(String powerId){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        CommonBase.SimpleResponse revokePowerResponse;
        try{
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            PowerRpcMessage.RevokePowerRequest joinRequest = PowerRpcMessage.RevokePowerRequest.newBuilder()
                    .setPowerId(powerId)
                    .build();
            //3.调用rpc,获取response
            revokePowerResponse = PowerServiceGrpc.newBlockingStub(channel).revokePower(joinRequest);
            //4.处理response
            if (revokePowerResponse.getStatus() != 0 || !GrpcConstant.ok.equals(revokePowerResponse.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        } finally {
            channelManager.closeChannel(channel);
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("停用算力接口, 响应时间:{}, 响应数据:{}", diffTime+"ms", revokePowerResponse.toString());
    }

    /**
     * 查看当前组织各个算力的详情 (如果有，则包含正在执行的任务信息)
     */
    public Pair<List<LocalPowerNode>, List<LocalPowerJoinTask>> getLocalPowerNodeListAndJoinTaskList(){
        Channel channel = null;
        try{
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            Empty request = Empty
                    .newBuilder()
                     .build();
            //3.调用rpc,获取response
            PowerRpcMessage.GetLocalPowerDetailListResponse response = PowerServiceGrpc.newBlockingStub(channel).getLocalPowerDetailList(request);
            //4.处理response
            if(GrpcConstant.GRPC_SUCCESS_CODE == response.getStatus()){
                return convertToLocalPowerNodeAndTaskJoinedList(response.getPowerListList());
            }else{
                log.error("查询本组织的算力主机，在当前正在执行的任务中的资源使用情况出错, code:{}, errorMsg:{}", response.getStatus(), response.getMsg());
                return null;
            }
        } finally {
            channelManager.closeChannel(channel);
        }
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
                localPowerJoinTask.setTaskStartTime(DateUtil.date(powerTask.getCreateAt()));   // 发起时间
                localPowerJoinTask.setUsedMemory(powerTask.getOperationSpend().getMemory());// 已使用内存
                localPowerJoinTask.setUsedCore(powerTask.getOperationSpend().getProcessor()); // 已使用核数
                localPowerJoinTask.setUsedBandwidth(powerTask.getOperationSpend().getBandwidth());// 已使用带宽
                return localPowerJoinTask;
            }).collect(Collectors.toList()));


        });
        return new ImmutablePair<>(localPowerNodeList, localPowerJoinTaskList);
    }



    /**
     * 获取全网算力信息
     * @return
     */
    public List<GlobalPower> getGlobalPowerDetailList(LocalDateTime latestSynced){
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            PowerRpcMessage.GetGlobalPowerDetailListRequest request = PowerRpcMessage.GetGlobalPowerDetailListRequest
                    .newBuilder()
                    .setLastUpdated(latestSynced.toInstant(ZoneOffset.UTC).toEpochMilli())
                    .build();
            //3.调用rpc,获取response
            PowerRpcMessage.GetGlobalPowerDetailListResponse response = PowerServiceGrpc.newBlockingStub(channel).getGlobalPowerDetailList(request);
            //4.处理response
            List<PowerRpcMessage.GetGlobalPowerDetailResponse> powerList = response.getPowerListList();
            List<GlobalPower> globalPowerList = new ArrayList<>();
            powerList.forEach(powerResponse -> {
                // 算力拥有者信息
                CommonBase.Organization owner = powerResponse.getOwner();
                String identityId = owner.getIdentityId();
                String orgName = owner.getNodeName();

                Resourcedata.PowerUsageDetail powerDetail = powerResponse.getPower();
                Resourcedata.ResourceUsageOverview information = powerDetail.getInformation();// 算力实况
                GlobalPower globalPower = new GlobalPower();
                globalPower.setId(powerResponse.getPowerId());
                globalPower.setIdentityId(identityId);
                globalPower.setOrgName(orgName);
                globalPower.setCore(information.getTotalProcessor());
                globalPower.setMemory(information.getTotalMem());
                globalPower.setBandwidth(information.getTotalBandwidth());
                globalPower.setUsedCore((int)information.getUsedProcessor());
                globalPower.setUsedMemory(information.getUsedMem());
                globalPower.setUsedBandwidth(information.getUsedBandwidth());
                globalPower.setStatus(powerDetail.getState().getNumber());
                globalPower.setPublished(powerDetail.getState().getNumber()==2 || powerDetail.getState().getNumber() ==3);
                globalPower.setPublishAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(powerDetail.getPublishAt()), ZoneOffset.UTC));
                globalPower.setUpdateAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(powerDetail.getUpdateAt()), ZoneOffset.UTC));
                globalPowerList.add(globalPower);
            });
            return globalPowerList;
        } finally {
            channelManager.closeChannel(channel);
        }
    }
}
