package com.platon.rosettanet.admin.dao.entity;

import com.platon.rosettanet.admin.dao.BaseDomain;

import java.time.LocalDateTime;

public class TaskResultReceiver extends BaseDomain {
    private String taskId;

    private String consumerIdentityId;

    private String producerIdentityId;

    private LocalDateTime recUpdateTime;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getConsumerIdentityId() {
        return consumerIdentityId;
    }

    public void setConsumerIdentityId(String consumerIdentityId) {
        this.consumerIdentityId = consumerIdentityId;
    }

    public String getProducerIdentityId() {
        return producerIdentityId;
    }

    public void setProducerIdentityId(String producerIdentityId) {
        this.producerIdentityId = producerIdentityId;
    }

    public LocalDateTime getRecUpdateTime() {
        return recUpdateTime;
    }

    public void setRecUpdateTime(LocalDateTime recUpdateTime) {
        this.recUpdateTime = recUpdateTime;
    }
}