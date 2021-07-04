package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class GlobalPower {
    private Integer id;

    private String identityId;

    private Integer totalCore;

    private Long totalMemory;

    private Long totalBandwidth;

    private Integer usedCore;

    private Long usedMemory;

    private Long usedBandwidth;

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

    public Integer getTotalCore() {
        return totalCore;
    }

    public void setTotalCore(Integer totalCore) {
        this.totalCore = totalCore;
    }

    public Long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public Long getTotalBandwidth() {
        return totalBandwidth;
    }

    public void setTotalBandwidth(Long totalBandwidth) {
        this.totalBandwidth = totalBandwidth;
    }

    public Integer getUsedCore() {
        return usedCore;
    }

    public void setUsedCore(Integer usedCore) {
        this.usedCore = usedCore;
    }

    public Long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(Long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public Long getUsedBandwidth() {
        return usedBandwidth;
    }

    public void setUsedBandwidth(Long usedBandwidth) {
        this.usedBandwidth = usedBandwidth;
    }

    public LocalDateTime getRecUpdateTime() {
        return recUpdateTime;
    }

    public void setRecUpdateTime(LocalDateTime recUpdateTime) {
        this.recUpdateTime = recUpdateTime;
    }
}