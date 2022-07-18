package com.platon.datum.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lyf
 * @Description 数据节点实体类
 * @date 2021/7/8 17:29
 */
@Data
public class DataNode {
    private String nodeId;

    private String nodeName;

    private String internalIp;

    private Integer internalPort;

    private String externalIp;

    private Integer externalPort;

    private Integer connStatus;

    private String connMessage;

    private LocalDateTime connTime;

    private String status;

    private String remarks;

    private LocalDateTime recCreateTime;

    private LocalDateTime recUpdateTime;
}