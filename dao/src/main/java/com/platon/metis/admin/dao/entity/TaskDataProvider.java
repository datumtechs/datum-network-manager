package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class TaskDataProvider extends BaseDomain {
    private String taskId;
    private String metaDataId;
    private String identityId;
    private String partyId;

}