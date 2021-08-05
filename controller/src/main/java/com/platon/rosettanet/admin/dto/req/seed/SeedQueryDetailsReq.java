package com.platon.rosettanet.admin.dto.req.seed;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 * 查询种子节点详情请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "查询种子节点详情请求参数")
public class SeedQueryDetailsReq {

    @NotNull(message = "种子节点ID不能为空")
    @ApiModelProperty(value = "种子节点ID", example = "", required = true)
    private String seedNodeId;

}
