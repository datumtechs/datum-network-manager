package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Task {
    private String id;

    private String taskName;

    private String ownerIdentityId;

    private LocalDateTime createAt;

    private LocalDateTime startAt;

    private LocalDateTime authAt;

    private String authStatus;

    private LocalDateTime endAt;

    private String status;

    private LocalDateTime duration;

    private Integer costCore;

    private Long costMemory;

    private Long costBandwidth;

    private String algIdentityId;

    private Boolean reviewed;

    private LocalDateTime recCreateTime;

    private LocalDateTime recUpdateTime;

    //任务发起方身份信息
    private TaskOrg owner;

    //算法提供方
    private TaskOrg algoSupplier;

    //结果接收方
    private List<TaskResultReceiver> receivers;

    //数据提供方
    private List<TaskDataReceiver> dataSupplier;

    //算力提供方
    private List<TaskPowerProvider> powerSupplier;

    //角色
    private Integer role;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOwnerIdentityId() {
        return ownerIdentityId;
    }

    public void setOwnerIdentityId(String ownerIdentityId) {
        this.ownerIdentityId = ownerIdentityId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDuration() {
        return duration;
    }

    public void setDuration(LocalDateTime duration) {
        this.duration = duration;
    }

    public Integer getCostCore() {
        return costCore;
    }

    public void setCostCore(Integer costCore) {
        this.costCore = costCore;
    }

    public Long getCostMemory() {
        return costMemory;
    }

    public void setCostMemory(Long costMemory) {
        this.costMemory = costMemory;
    }

    public Long getCostBandwidth() {
        return costBandwidth;
    }

    public void setCostBandwidth(Long costBandwidth) {
        this.costBandwidth = costBandwidth;
    }

    public String getAlgIdentityId() {
        return algIdentityId;
    }

    public void setAlgIdentityId(String algIdentityId) {
        this.algIdentityId = algIdentityId;
    }

    public Boolean getReviewed() {
        return reviewed;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
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

    public TaskOrg getOwner() {
        return owner;
    }

    public void setOwner(TaskOrg owner) {
        this.owner = owner;
    }

    public TaskOrg getAlgoSupplier() {
        return algoSupplier;
    }

    public void setAlgoSupplier(TaskOrg algoSupplier) {
        this.algoSupplier = algoSupplier;
    }

    public List<TaskResultReceiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<TaskResultReceiver> receivers) {
        this.receivers = receivers;
    }

    public List<TaskDataReceiver> getDataSupplier() {
        return dataSupplier;
    }

    public void setDataSupplier(List<TaskDataReceiver> dataSupplier) {
        this.dataSupplier = dataSupplier;
    }

    public List<TaskPowerProvider> getPowerSupplier() {
        return powerSupplier;
    }

    public void setPowerSupplier(List<TaskPowerProvider> powerSupplier) {
        this.powerSupplier = powerSupplier;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}