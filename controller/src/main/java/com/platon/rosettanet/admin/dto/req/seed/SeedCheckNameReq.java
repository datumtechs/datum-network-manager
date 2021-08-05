package com.platon.rosettanet.admin.dto.req.seed;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 * 种子节点名称校验请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "种子节点名称校验请求参数")
public class SeedCheckNameReq {

    @NotNull(message = "种子节点名称不能为空")
    @ApiModelProperty(value = "种子节点名称", example = "", required = true)
    private String seedNodeName;


}
