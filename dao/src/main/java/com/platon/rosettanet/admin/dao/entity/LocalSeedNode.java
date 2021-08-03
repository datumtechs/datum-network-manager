package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 种子节点实体类
 */
@Data
public class LocalSeedNode {

    private Integer id;

    private String identityId;

    private String seedNodeId;

    private String seedNodeName;

    private String internalIp;

    private Integer internalPort;

    private Integer connStatus;

    private Integer initFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}