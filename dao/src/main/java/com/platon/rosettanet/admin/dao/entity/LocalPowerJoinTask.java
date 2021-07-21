package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

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

    private String ownerIdentityName;

    private Date taskStartTime;

    private String resultSideName;

    private String resultSideId;

    private String coordinateSideId;

    private String coordinateSideName;

    private Long usedMemory;

    private Integer usedCore;

    private Long usedBandwidth;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}