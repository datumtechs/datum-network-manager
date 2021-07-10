package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

/**
 * @author lyf
 * @Description 数据节点实体类
 * @date 2021/7/8 17:29
 */
public class DataNode {
    private Integer id;

    private String identityId;

    private String nodeId;

    private String hostName;

    private String internalIp;

    private Integer internalPort;

    private String externalIp;

    private Integer externalPort;

    private String connStatus;

    private String connMessage;

    private LocalDateTime connTime;

    private String status;

    private String remarks;

    private LocalDateTime recCreateTime;

    private LocalDateTime recUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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

    public String getConnMessage() {
        return connMessage;
    }

    public void setConnMessage(String connMessage) {
        this.connMessage = connMessage;
    }

    public LocalDateTime getConnTime() {
        return connTime;
    }

    public void setConnTime(LocalDateTime connTime) {
        this.connTime = connTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getRecCreateTime() {
        return recCreateTime;
    }

    public void setRecCreateTime(LocalDateTime recCreateTime) {
        this.recCreateTime = recCreateTime;
    }

    public LocalDateTime getRecUpdateTime() {
        return recUpdateTime;
    }

    public void setRecUpdateTime(LocalDateTime recUpdateTime) {
        this.recUpdateTime = recUpdateTime;
    }
}