package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class Task extends BaseDomain {
    private Integer id;

    private String taskId;

    private String taskName;

    private String ownerIdentityId;
    private String ownerPartyId;

    private LocalDateTime createAt;

    private LocalDateTime startAt;

    private LocalDateTime authAt;

    private String authStatus;

    private LocalDateTime endAt;
    //任务状态(0:unknown未知、1:pending等在中、2:running计算中、3:failed失败、4:success成功)
    private Integer status;

    private Long duration;

    private Integer costCore;

    private Long costMemory;

    private Long costBandwidth;

    private Boolean reviewed;

    private LocalDateTime recCreateTime;

    private LocalDateTime recUpdateTime;

    //任务发起方身份信息
    private List<TaskOrg> taskOrgList;

    private TaskOrg owner;

    private TaskAlgoProvider algoSupplier;

    //结果接收方
    private List<TaskResultConsumer> receivers;

    //数据提供方
    private List<TaskDataProvider> dataSupplier;

    //算力提供方
    private List<TaskPowerProvider> powerSupplier;

    //我在任务中的角色 (0：unknown 未知、1： owner  任务发起方、2：dataSupplier  数据提供方、 3: powerSupplier  算力提供方、 4： receiver  结果接收方、5：algoSupplier 算法提供方
    //private String role;

    //发起任务的用户的信息 (task是属于用户的)
    private String applyUser;

    //用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)
    private Integer userType;



}