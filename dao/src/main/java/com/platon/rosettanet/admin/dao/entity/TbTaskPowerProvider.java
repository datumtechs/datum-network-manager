package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class TbTaskPowerProvider {
    private Integer id;

    private String taskId;

    private String taskName;

    private String ownerName;

    private String ownerIdentity;

    private Integer usedProcessor;

    private Long usedMem;

    private Long usedBandwidth;

    private LocalDateTime lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
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

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}