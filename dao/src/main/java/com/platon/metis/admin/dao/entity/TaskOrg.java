package com.platon.metis.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@ToString
public class TaskOrg {
    //机构身份标识ID
    private String identityId;
    //机构名称
    private String name;
    //组织中调度服务的 nodeId
    private String carrierNodeId;
    //更新时间
    private LocalDateTime recUpdateTime;

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