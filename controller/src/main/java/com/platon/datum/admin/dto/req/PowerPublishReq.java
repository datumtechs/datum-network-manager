package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author houzhuang
 * 停用算力请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "启用算力请求参数")
public class PowerPublishReq {

    @NotBlank(message = "计算节点ID不能为空")
    @ApiModelProperty(value = "计算节点ID", example = "", required = true)
    private String powerNodeId;

}
