package com.platon.metis.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 * 计算节点查询详情请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "计算节点查询详情请求参数")
public class PowerQueryDetailsReq {


    @NotNull(message = "计算节点ID不能为空")
    @ApiModelProperty(value = "计算节点ID", example = "", required = true)
    private String powerNodeId;
}
