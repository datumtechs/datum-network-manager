package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 计算节点实体类
 */
@Data
public class LocalPowerNode {

    private Integer id;

    private String identityId;

    private String powerNodeId;

    private String powerNodeName;

    private String internalIp;

    private Integer internalPort;

    private String externalIp;

    private Integer externalPort;

    private LocalDateTime startTime;

    private String remarks;

    private String connStatus;

    private String connMessage;

    private LocalDateTime connTime;

    private String status;

    private Long memory;

    private Integer core;

    private Long bandwidth;

    private Long usedMemory;

    private Integer usedCore;

    private Long usedBandwidth;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}