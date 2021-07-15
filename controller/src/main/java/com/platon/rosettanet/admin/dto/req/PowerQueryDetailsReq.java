package com.platon.rosettanet.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 * 计算节点查询详情请求参数
 */
@Data
@ApiModel(value = "计算节点查询详情请求参数")
public class PowerQueryDetailsReq {


    @NotNull(message = "计算节点ID不能为空")
    @ApiModelProperty(value = "计算节点ID", example = "", required = true)
    private String powerNodeId;
}
