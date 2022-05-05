package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@ApiModel(value = "任务信息")
public class Task extends BaseDomain {

    @ApiModelProperty(name = "id", value = "task记录id")
    private Integer id;

    @ApiModelProperty(name = "taskId", value = "taskId")
    private String taskId;

    @ApiModelProperty(name = "taskName", value = "任务名称")
    private String taskName;

    @ApiModelProperty(name = "ownerIdentityId", value = "任务发起者identityId")
    private String ownerIdentityId;

    @ApiModelProperty(name = "ownerPartyId", value = "任务发起者在任务中的partyId")
    private String ownerPartyId;

    @ApiModelProperty(name = "createAt", value = "任务创建时间")
    private LocalDateTime createAt;

    @ApiModelProperty(name = "startAt", value = "任务开始时间")
    private LocalDateTime startAt;

    @ApiModelProperty(name = "authAt", value = "任务授权时间")
    private LocalDateTime authAt;

    @ApiModelProperty(name = "authStatus", value = "任务授权状态(pending:等待授权、denied:授权未通过)")
    private String authStatus;

    @ApiModelProperty(name = "endAt", value = "任务结束时间")
    private LocalDateTime endAt;
    //任务状态(0:unknown未知、1:pending等在中、2:running计算中、3:failed失败、4:success成功)

    @ApiModelProperty(name = "status", value = "任务状态(0:unknown未知、1:pending等在中、2:running计算中、3:failed失败、4:success成功)")
    private Integer status;

    @ApiModelProperty(name = "duration", value = "任务声明计算时间")
    private Long duration;

    @ApiModelProperty(name = "costCore", value = "任务声明所需cpu核心")
    private Integer costCore;

    @ApiModelProperty(name = "costMemory", value = "任务声明所需内存")
    private Long costMemory;

    @ApiModelProperty(name = "costBandwidth", value = "任务声明所需带宽")
    private Long costBandwidth;

    @ApiModelProperty(name = "reviewed", value = "任务是否被查看过，默认为false(0)")
    private Boolean reviewed;

    @ApiModelProperty(name = "updateAt", value = "最近更新时间")
    private LocalDateTime updateAt;

    //任务发起方身份信息
    private List<TaskOrg> taskOrgList;

    @ApiModelProperty(name = "owner", value = "任务发起者信息")
    private TaskOrg owner;

    @ApiModelProperty(name = "algoSupplier", value = "任务算法提供者信息")
    private TaskAlgoProvider algoSupplier;

    @ApiModelProperty(name = "receivers", value = "任务结果接收者列表")
    private List<TaskResultConsumer> receivers;

    @ApiModelProperty(name = "dataSupplier", value = "任务数据提供者列表")
    private List<TaskDataProvider> dataSupplier;

    @ApiModelProperty(name = "powerSupplier", value = "任务算力提供者列表")
    private List<TaskPowerProvider> powerSupplier;

    //我在任务中的角色 (0：unknown 未知、1： owner  任务发起方、2：dataSupplier  数据提供方、 3: powerSupplier  算力提供方、 4： receiver  结果接收方、5：algoSupplier 算法提供方
    //private String role;

    //发起任务的用户的信息 (task是属于用户的)
    @ApiModelProperty(name = "applyUser", value = "任务发起者的userId")
    private String applyUser;

    //用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)
    @ApiModelProperty(name = "userType", value = "任务发起者用户类型(0: 未定义; 1: 第三方地址; 2: 测试网地址; 3: 主网地址)")
    private Integer userType;



}