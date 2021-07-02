package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class TbTask {
    private Integer id;

    private String taskId;

    private String taskName;

    private String ownerName;

    private String ownerIdentity;

    private LocalDateTime createAt;

    private LocalDateTime startAt;

    private LocalDateTime authAt;

    private String authStatus;

    private LocalDateTime endAt;

    private String state;

    private LocalDateTime duration;

    private Integer costProcessor;

    private Long costMem;

    private Long costBandwidth;

    private String alogSupplierIdentity;

    private String alogSupplierName;

    private String seen;

    private LocalDateTime createTime;

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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getAuthAt() {
        return authAt;
    }

    public void setAuthAt(LocalDateTime authAt) {
        this.authAt = authAt;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getDuration() {
        return duration;
    }

    public void setDuration(LocalDateTime duration) {
        this.duration = duration;
    }

    public Integer getCostProcessor() {
        return costProcessor;
    }

    public void setCostProcessor(Integer costProcessor) {
        this.costProcessor = costProcessor;
    }

    public Long getCostMem() {
        return costMem;
    }

    public void setCostMem(Long costMem) {
        this.costMem = costMem;
    }

    public Long getCostBandwidth() {
        return costBandwidth;
    }

    public void setCostBandwidth(Long costBandwidth) {
        this.costBandwidth = costBandwidth;
    }

    public String getAlogSupplierIdentity() {
        return alogSupplierIdentity;
    }

    public void setAlogSupplierIdentity(String alogSupplierIdentity) {
        this.alogSupplierIdentity = alogSupplierIdentity;
    }

    public String getAlogSupplierName() {
        return alogSupplierName;
    }

    public void setAlogSupplierName(String alogSupplierName) {
        this.alogSupplierName = alogSupplierName;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
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