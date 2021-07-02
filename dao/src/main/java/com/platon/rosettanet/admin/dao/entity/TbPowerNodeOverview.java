package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class TbPowerNodeOverview {
    private Integer id;

    private String nodeId;

    private Integer totalProcessor;

    private Long totalMem;

    private Long totalBandwidth;

    private String desc;

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

    public Integer getTotalProcessor() {
        return totalProcessor;
    }

    public void setTotalProcessor(Integer totalProcessor) {
        this.totalProcessor = totalProcessor;
    }

    public Long getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(Long totalMem) {
        this.totalMem = totalMem;
    }

    public Long getTotalBandwidth() {
        return totalBandwidth;
    }

    public void setTotalBandwidth(Long totalBandwidth) {
        this.totalBandwidth = totalBandwidth;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}