package com.platon.rosettanet.admin.dao.entity;

import com.platon.rosettanet.admin.dao.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TaskResultReceiver extends BaseDomain {
    private String taskId;

    private String consumerIdentityId;

    private String producerIdentityId;

    private LocalDateTime recUpdateTime;


}