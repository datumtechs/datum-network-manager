package com.platon.rosettanet.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 * 删除计算节点详情请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "删除计算节点请求参数")
public class PowerDeleteReq {

    @NotNull(message = "计算节点ID不能为空")
    @ApiModelProperty(value = "计算节点ID", example = "", required = true)
    private String powerNodeId;

}
