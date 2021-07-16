package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class Task {
    private String id;

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

    //角色
    private Integer role;




}