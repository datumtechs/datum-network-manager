package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskResultConsumer extends BaseDomain {
    private String taskId;

    private String consumerIdentityId;
    private String consumerPartyId;

    private String producerIdentityId;
    private String producerPartyId;


}