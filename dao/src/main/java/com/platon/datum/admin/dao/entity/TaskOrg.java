package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel(value = "任务的计算结果接收者")
public class TaskOrg extends BaseDomain {
    @ApiModelProperty(name = "identityId", value = "组织ID")
    private String identityId;

    @ApiModelProperty(name = "orgName", value = "组织名称")
    private String orgName;

    //组织中调度服务的 nodeId
    @ApiModelProperty(name = "carrierNodeId", value = "组织中调度服务ID")
    private String carrierNodeId;

    public TaskOrg() {
    }

    public TaskOrg(String identityId, String orgName, String carrierNodeId) {
        this.identityId = identityId;
        this.orgName = orgName;
        this.carrierNodeId = carrierNodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskOrg taskOrg = (TaskOrg) o;
        return identityId.equals(taskOrg.identityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identityId);
    }
}