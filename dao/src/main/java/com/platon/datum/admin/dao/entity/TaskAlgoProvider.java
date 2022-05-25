package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "任务的算法提供者")
public class TaskAlgoProvider extends BaseDomain {
    @ApiModelProperty(name = "taskId", value = "任务ID")
    private String taskId;

    @ApiModelProperty(name = "identityId", value = "算法提供者的组织ID")
    private String identityId;

    @ApiModelProperty(name = "partyId", value = "算法提供者在任务中的party ID")
    private String partyId;
}
