package com.platon.datum.admin.dto.req.seed;


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
public class CheckSeedNodeIdReq {

    @NotNull(message = "种子节点ID不能为空")
    @ApiModelProperty(value = "种子节点ID", example = "/ip4/192.168.9.155/tcp/18001/p2p/16Uiu2HAm291kstk4F64bEEuEQqNhLwnDhR4dCnYB4nQawqhixY9f", required = true)
    private String seedNodeId;


}
