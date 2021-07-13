package com.platon.rosettanet.admin.dao.entity;

import com.platon.rosettanet.admin.dao.BaseDomain;

import java.time.LocalDateTime;

public class TaskPowerProvider extends BaseDomain {
    private String taskId;

    private String identityId;

    private Long usedCore;

    private Long usedMemory;

    private Long usedBandwidth;

    private LocalDateTime recUpdateTime;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public Long getUsedCore() {
        return usedCore;
    }

    public void setUsedCore(Long usedCore) {
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