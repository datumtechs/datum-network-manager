package com.platon.metis.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 种子节点实体类
 */
@Data
public class LocalSeedNode {

    private Integer id;

    /** 组织id */
    private String identityId;

    /** 外部节点id */
    private String outNodeId;

    /** 种子节点id */
    private String seedNodeId;

    /** 种子节点名称 */
    private String seedNodeName;

    /** 内部ip */
    private String internalIp;

    /** 内部端口 */
    private Integer internalPort;

    /** 连接状态 */
    private Integer connStatus;

    /** 是否初始节点 */
    private Integer initFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}