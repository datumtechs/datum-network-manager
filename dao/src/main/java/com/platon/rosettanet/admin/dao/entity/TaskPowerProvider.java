package com.platon.rosettanet.admin.dao.entity;

import com.platon.rosettanet.admin.dao.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TaskPowerProvider extends BaseDomain {
    private String taskId;

    private String identityId;

    private Integer totalCore;

    private Integer usedCore;

    private Long totalMemory;

    private Long usedMemory;

    private Long totalBandwidth;

    private Long usedBandwidth;

    private LocalDateTime recUpdateTime;

}