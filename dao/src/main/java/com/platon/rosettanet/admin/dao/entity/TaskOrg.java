package com.platon.rosettanet.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class TaskOrg {
    //任务ID,hash
    private String taskId;
    //机构名称
    private String name;
    //机构身份标识ID
    private String identityId;
    //组织中调度服务的 nodeId
    private String carrierNodeId;
    //更新时间
    private LocalDateTime recUpdateTime;



}