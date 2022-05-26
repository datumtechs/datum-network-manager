package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "任务的算力提供者")
public class TaskPowerProvider extends BaseDomain {

    @ApiModelProperty(name = "taskId", value = "任务ID")
    private String taskId;

    @ApiModelProperty(name = "identityId", value = "算力提供者的组织ID")
    private String identityId;

    @ApiModelProperty(name = "partyId", value = "算力提供者在任务中的part ID")
    private String partyId;

    @ApiModelProperty(name = "totalCore", value = "算力提供者提供的可用核心数")
    private Integer totalCore;

    @ApiModelProperty(name = "usedCore", value = "任务实际使用的核心数")
    private Integer usedCore;

    @ApiModelProperty(name = "totalMemory", value = "算力提供者提供的可用内存数")
    private Long totalMemory;

    @ApiModelProperty(name = "usedMemory", value = "任务实际使用的内存数")
    private Long usedMemory;

    @ApiModelProperty(name = "totalBandwidth", value = "算力提供者提供的可用带宽数")
    private Long totalBandwidth;

    @ApiModelProperty(name = "usedBandwidth", value = "任务实际使用的带宽数")
    private Long usedBandwidth;

}