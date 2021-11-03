package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "任务的计算结果接收者")
public class TaskResultConsumer extends BaseDomain {
    @ApiModelProperty(name = "taskId", value = "任务ID")
    private String taskId;
    @ApiModelProperty(name = "consumerIdentityId", value = "结果接收者的组织ID")
    private String consumerIdentityId;

    @ApiModelProperty(name = "consumerPartyId", value = "结果接收者在任务中的party ID")
    private String consumerPartyId;

    private String producerIdentityId;
    private String producerPartyId;


}