package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author houz
 * 计算节点查询详情请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "计算节点查询详情请求参数")
public class PowerQueryDetailsReq {


    @NotBlank(message = "计算节点ID不能为空")
    @ApiModelProperty(value = "计算节点ID", example = "", required = true)
    private String powerNodeId;
}
