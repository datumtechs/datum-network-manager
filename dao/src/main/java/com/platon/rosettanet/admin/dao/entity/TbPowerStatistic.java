package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class TbPowerStatistic {
    private Integer id;

    private String identityId;

    private Integer usedProcessor;

    private Long usedMem;

    private Long usedBandwidth;

    private LocalDateTime createTime;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}