package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 * 删除种子节点请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "删除种子节点请求参数")
public class DeleteSeedNodeReq {

    @NotNull(message = "种子节点ID不能为空")
    @ApiModelProperty(value = "种子节点ID", example = "", required = true)
    private String seedNodeId;

}