package com.platon.rosettanet.admin.dto.req;

import com.platon.rosettanet.admin.dto.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 本组织数据所参与的任务
 */

@Data
@ApiModel
public class LocalDataJoinTaskListReq extends CommonPageReq {

    @ApiModelProperty(value = "元数据ID",required = true)
    @NotNull(message = "metaDataId不能为空")
    private String metaDataId;
    @ApiModelProperty(value = "关键字",required = false)
    private String keyword;
}
