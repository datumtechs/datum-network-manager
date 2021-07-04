package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class GlobalOrg {
    private Integer id;

    private String name;

    private String identityId;

    private String carrierUuid;

    private String status;

    private LocalDateTime recUpdateTime;

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

    public String getCarrierUuid() {
        return carrierUuid;
    }

    public void setCarrierUuid(String carrierUuid) {
        this.carrierUuid = carrierUuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRecUpdateTime() {
        return recUpdateTime;
    }

    public void setRecUpdateTime(LocalDateTime recUpdateTime) {
        this.recUpdateTime = recUpdateTime;
    }
}