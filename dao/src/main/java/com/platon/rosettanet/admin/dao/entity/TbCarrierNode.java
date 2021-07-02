package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class TbCarrierNode {
    private Integer id;

    private String name;

    private String identityId;

    private String nodeId;

    private String carrierIp;

    private Integer carrierPort;

    private String connStatus;

    private String carrierStatus;

    private String desc;

    private LocalDateTime connTime;

    private LocalDateTime lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCarrierIp() {
        return carrierIp;
    }

    public void setCarrierIp(String carrierIp) {
        this.carrierIp = carrierIp;
    }

    public Integer getCarrierPort() {
        return carrierPort;
    }

    public void setCarrierPort(Integer carrierPort) {
        this.carrierPort = carrierPort;
    }

    public String getConnStatus() {
        return connStatus;
    }

    public void setConnStatus(String connStatus) {
        this.connStatus = connStatus;
    }

    public String getCarrierStatus() {
        return carrierStatus;
    }

    public void setCarrierStatus(String carrierStatus) {
        this.carrierStatus = carrierStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getConnTime() {
        return connTime;
    }

    public void setConnTime(LocalDateTime connTime) {
        this.connTime = connTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}