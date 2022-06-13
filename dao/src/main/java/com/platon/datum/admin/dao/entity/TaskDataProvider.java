package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "任务的数据提供者")
public class TaskDataProvider extends BaseDomain {

    @ApiModelProperty(name = "hash", value = "hash")
    private String hash;

    @ApiModelProperty(name = "taskId", value = "任务ID")
    private String taskId;

    @ApiModelProperty(name = "metaDataId", value = "数据提供者提供的元数据ID")
    private String metaDataId;

    @ApiModelProperty(name = "metaDataName", value = "数据提供者提供的元数据名称")
    private String metaDataName;

    @ApiModelProperty(name = "identityId", value = "数据提供者的组织ID")
    private String identityId;

    @ApiModelProperty(name = "partyId", value = "数据提供者在任务中的party ID")
    private String partyId;

}