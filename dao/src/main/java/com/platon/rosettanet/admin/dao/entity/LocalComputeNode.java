package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

@Date
public class LocalComputeNode {

    private Integer id;

    private String identityId;

    private String nodeId;

    private String nodeName;

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

    private LocalDateTime recCreateTime;

    private LocalDateTime recUpdateTime;

}