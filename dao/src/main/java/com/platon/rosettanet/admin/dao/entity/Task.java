package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class Task {
    private Integer id;

    private String taskId;

    private String taskName;

    private String ownerIdentityId;

    private LocalDateTime createAt;

    private LocalDateTime startAt;

    private LocalDateTime authAt;

    private String authStatus;

    private LocalDateTime endAt;

    private String status;

    private LocalDateTime duration;

    private Long costCore;

    private Long costMemory;

    private Long costBandwidth;

    private String algIdentityId;

    private Boolean reviewed;

    private LocalDateTime recCreateTime;

    private LocalDateTime recUpdateTime;

    //任务发起方身份信息
    private TaskOrg owner;

    //算法提供方
    private TaskOrg algoSupplier;

    //结果接收方
    private List<TaskResultReceiver> receivers;

    //数据提供方
    private List<TaskDataReceiver> dataSupplier;

    //算力提供方
    private List<TaskPowerProvider> powerSupplier;

    //我在任务中的角色 (owner: 任务发起方; dataSupplier: 数据提供方: powerSupplier: 算力提供方; receiver: 结果接收方; algoSupplier:算法提供方)
    private String role;

    //发起任务的用户的信息 (task是属于用户的)
    private String applyUser;

    //用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)
    private Integer userType;



}