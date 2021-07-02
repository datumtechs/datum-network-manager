package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class TbGlobalPower {
    private Integer id;

    private String identityId;

    private String nodeName;

    private String state;

    private Integer usedProcessor;

    private Long usedMem;

    private Long usedBandwidth;

    private Integer totalProcessor;

    private Long totalMem;

    private Long totalBandwidth;

    private LocalDateTime lastUpdateTime;

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

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getUsedProcessor() {
        return usedProcessor;
    }

    public void setUsedProcessor(Integer usedProcessor) {
        this.usedProcessor = usedProcessor;
    }

    public Long getUsedMem() {
        return usedMem;
    }

    public void setUsedMem(Long usedMem) {
        this.usedMem = usedMem;
    }

    public Long getUsedBandwidth() {
        return usedBandwidth;
    }

    public void setUsedBandwidth(Long usedBandwidth) {
        this.usedBandwidth = usedBandwidth;
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

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}