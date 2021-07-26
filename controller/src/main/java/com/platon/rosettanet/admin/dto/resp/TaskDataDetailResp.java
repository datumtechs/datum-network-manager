package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.constant.ControllerConstants;
import com.platon.rosettanet.admin.dao.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "任务详情实体类")
public class TaskDataDetailResp {


    @ApiModelProperty(name = "id",value = "序号")
    private Integer id;
    @ApiModelProperty(name = "taskId",value = "任务id")
    private String taskId;
    @ApiModelProperty(name = "taskName",value = "任务名称")
    private String taskName;
    @ApiModelProperty(name = "createAt",value = "任务发起时间 (时间戳)，单位ms")
    private Long createAt;
    @ApiModelProperty(name = "startAt",value = "任务开始计算时间 (时间戳)，单位ms")
    private Long startAt;
    @ApiModelProperty(name = "endAt",value = "任务结束时间 (时间戳)，单位ms")
    private Long endAt;
    @ApiModelProperty(name = "status",value = "任务状态 (pending: 等在中; failed: 失败 (计算结束); success: 成功 (计算结束))")
    private String status;
    @ApiModelProperty(name = "duration",value = "任务所需资源声明，任务运行耗时时长 (单位: ms)")
    private Long duration;
    @ApiModelProperty(name = "costCore",value = "任务所需的CPU资源 (单位: 个)")
    private Long costCore;
    @ApiModelProperty(name = "costMemory",value = "任务所需的内存资源 (单位: byte)")
    private Long costMemory;
    @ApiModelProperty(name = "costBandwidth",value = "任务所需的带宽资源 (单位: bps)")
    private Long costBandwidth;
    @ApiModelProperty(name = "reviewed",value = "任务是否被查看过，默认为false(0)")
    private Boolean reviewed;
    @ApiModelProperty(name = "role",value = "我在任务中的角色 (0: 未定义; 1: 发起方; 2: 数据提供方; 3: 计算参与方; 4: 结果提供方)")
    private Integer role;
    @ApiModelProperty(name = "owner",value = "任务发起方身份信息")
    //任务发起方身份信息
    private CommonTaskOrg owner;
    @ApiModelProperty(name = "algoSupplier",value = "算法提供方身份信息")
    //算法提供方
    private CommonTaskOrg algoSupplier;
    @ApiModelProperty(name = "receivers",value = "结果接收方身份信息")
    //结果接收方
    private List<CommonTaskOrg> receivers;
    @ApiModelProperty(name = "dataSupplier",value = "数据提供方身份信息")
    //数据提供方
    private List<DataSupplier> dataSupplier;
    @ApiModelProperty(name = "powerSupplier",value = "算力提供方")
    //算力提供方
    private List<PowerSupplier> powerSupplier;


    public static TaskDataDetailResp convert(Task task){
        TaskDataDetailResp resp = new TaskDataDetailResp();
        resp.setId(task.getId());
        resp.setTaskId(task.getTaskId());
        resp.setTaskName(task.getTaskName());
        resp.setRole(TaskDataPageResp.getRole(task.getRole()));
        resp.setStatus(task.getStatus());
        resp.setReviewed(task.getReviewed());
        resp.setCostBandwidth(task.getCostBandwidth());
        resp.setCostCore(task.getCostCore());
        resp.setCostMemory(task.getCostMemory());
        resp.setCreateAt(task.getCreateAt() == null ? null : task.getCreateAt().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        resp.setDuration(task.getDuration() == null ? null : task.getDuration().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        resp.setStartAt(task.getStartAt() == null ? null : task.getStartAt().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        resp.setEndAt(task.getEndAt() == null ? null : task.getEndAt().toInstant(ZoneOffset.of("+8")).toEpochMilli());

        //owner
        CommonTaskOrg owner = new CommonTaskOrg();
        if(task.getOwner() != null){
            owner.setNodeName(task.getOwner().getName());
            owner.setNodeIdentityId(task.getOwner().getIdentityId());
            owner.setCarrierNodeId(task.getOwner().getCarrierNodeId());
            //BeanUtils.copyProperties(task.getOwner(),owner);
        }
        resp.setOwner(owner);

        //PowerSupplier
        List<PowerSupplier> powerSupplierList = new ArrayList<>();
        List<TaskPowerProvider> taskPowerProviderList = task.getPowerSupplier();
        for (TaskPowerProvider powerProvider : taskPowerProviderList) {
            PowerSupplier powerSupplier = new PowerSupplier();
            if(powerProvider != null){
                BeanUtils.copyProperties(powerProvider,powerSupplier);
            }
            powerSupplier.setCarrierNodeId(powerProvider.getDynamicFields().get(ControllerConstants.NODE_ID).toString());
            powerSupplier.setNodeIdentityId(powerProvider.getDynamicFields().get(ControllerConstants.NODE_IDENTITY_ID).toString());
            powerSupplier.setNodeName(powerProvider.getDynamicFields().get(ControllerConstants.NODE_NAME).toString());
            powerSupplierList.add(powerSupplier);
        }
        resp.setPowerSupplier(powerSupplierList);

         //Receivers
        List<CommonTaskOrg> receiverList = new ArrayList<>();
        List<TaskResultReceiver> taskResultReceiverList = task.getReceivers();
        for (TaskResultReceiver  resultReceiver : taskResultReceiverList) {
                CommonTaskOrg receiver = new CommonTaskOrg();
                if(resultReceiver != null){
                    receiver.setCarrierNodeId(resultReceiver.getDynamicFields().get(ControllerConstants.NODE_ID).toString());
                    receiver.setNodeIdentityId(resultReceiver.getDynamicFields().get(ControllerConstants.NODE_IDENTITY_ID).toString());
                    receiver.setNodeName(resultReceiver.getDynamicFields().get(ControllerConstants.NODE_NAME).toString());
                    receiverList.add(receiver);
                }
        }
        resp.setReceivers(receiverList);

        //algoSupplier
        CommonTaskOrg algoSupplier = new CommonTaskOrg();
        if(task.getAlgoSupplier() != null){
            algoSupplier.setNodeName(task.getAlgoSupplier().getName());
            algoSupplier.setCarrierNodeId(task.getAlgoSupplier().getCarrierNodeId());
            algoSupplier.setNodeIdentityId(task.getAlgoSupplier().getIdentityId());
            //BeanUtils.copyProperties(task.getAlgoSupplier(),algoSupplier);
        }
        resp.setAlgoSupplier(algoSupplier);

        //DataSupplier
        List<DataSupplier> dataSupplierList = new ArrayList<>();
        List<TaskDataReceiver> taskDataReceiverList = task.getDataSupplier();
        for (TaskDataReceiver dataReceiver : taskDataReceiverList) {
              DataSupplier  dataSupplier = new DataSupplier();
              if(dataReceiver != null){
                  BeanUtils.copyProperties(dataReceiver, dataSupplier);
                  dataSupplier.setCarrierNodeId(dataReceiver.getDynamicFields().get(ControllerConstants.NODE_ID).toString());
                  dataSupplier.setNodeIdentityId(dataReceiver.getDynamicFields().get(ControllerConstants.NODE_IDENTITY_ID).toString());
                  dataSupplier.setNodeName(dataReceiver.getDynamicFields().get(ControllerConstants.NODE_NAME).toString());
                  dataSupplierList.add(dataSupplier);
              }
        }
        resp.setDataSupplier(dataSupplierList);
        return resp;
    }

     @Data
     public static class CommonTaskOrg{
         @ApiModelProperty(name = "carrierNodeId",value = "组织中调度服务的 nodeId")
         String carrierNodeId;
         @ApiModelProperty(name = "nodeIdentityId",value = "节点组织身份标识ID")
         String nodeIdentityId;
         @ApiModelProperty(name = "nodeName",value = "组织名称")
         String nodeName;
    }

    @Data
    public static class DataSupplier extends CommonTaskOrg{
        @ApiModelProperty(name = "metaDataId",value = "参与任务的元数据ID")
        private String metaDataId;
        @ApiModelProperty(name = "metaDataName",value = "元数据名称")
        private String metaDataName;
    }


   @Data
    public static class PowerSupplier extends CommonTaskOrg{
        @ApiModelProperty(name = "usedBandwidth",value = "任务占用带宽信息")
        private String usedBandwidth;
        @ApiModelProperty(name = "usedCore",value = "任务占用CPU信息")
        private String usedCore;
        @ApiModelProperty(name = "usedMemory",value = "任务占用内存信息")
        private String usedMemory;

    }









}
