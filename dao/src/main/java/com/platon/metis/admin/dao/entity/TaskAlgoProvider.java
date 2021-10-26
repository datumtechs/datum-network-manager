package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;

public class TaskAlgoProvider extends BaseDomain {
    private String taskId;

    private String identityId;

    private String partyId;

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

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
