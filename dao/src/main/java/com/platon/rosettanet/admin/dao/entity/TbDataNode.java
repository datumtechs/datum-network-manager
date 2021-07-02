package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class TbDataNode {
    private Integer id;

    private String nodeId;

    private String nodeName;

    private String internalIp;

    private Integer internalPort;

    private String externalIp;

    private Integer externalPort;

    private String connStatus;

    private String status;

    private LocalDateTime lastConnTime;

    private String desc;

    private LocalDateTime createTime;

    private LocalDateTime lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }

    public Integer getInternalPort() {
        return internalPort;
    }

    public void setInternalPort(Integer internalPort) {
        this.internalPort = internalPort;
    }

    public String getExternalIp() {
        return externalIp;
    }

    public void setExternalIp(String externalIp) {
        this.externalIp = externalIp;
    }

    public Integer getExternalPort() {
        return externalPort;
    }

    public void setExternalPort(Integer externalPort) {
        this.externalPort = externalPort;
    }

    public String getConnStatus() {
        return connStatus;
    }

    public void setConnStatus(String connStatus) {
        this.connStatus = connStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastConnTime() {
        return lastConnTime;
    }

    public void setLastConnTime(LocalDateTime lastConnTime) {
        this.lastConnTime = lastConnTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}