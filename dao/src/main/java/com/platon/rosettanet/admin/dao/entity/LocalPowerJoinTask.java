package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 计算节点实体类
 */
@Data
public class LocalPowerJoinTask {

    private Integer id;

    private String powerNodeId;

    private String taskId;

    private String taskName;

    private String ownerIdentityId;

    private LocalDateTime taskStartTime;

    private String resultSide;

    private String coordinateSide;

    private Long usedMemory;

    private Integer usedCore;

    private Long usedBandwidth;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}