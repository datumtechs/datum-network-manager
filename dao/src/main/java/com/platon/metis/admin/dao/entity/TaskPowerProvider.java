package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskPowerProvider extends BaseDomain {
    private String taskId;

    private String identityId;
    private String partyId;

    private Integer totalCore;

    private Integer usedCore;

    private Long totalMemory;

    private Long usedMemory;

    private Long totalBandwidth;

    private Long usedBandwidth;

}