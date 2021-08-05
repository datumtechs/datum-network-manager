package com.platon.rosettanet.admin.dto.req.seed;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houzhuang
 * 修改种子节点请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "修改种子节点请求参数")
public class SeedUpdateReq {

    @NotNull(message = "种子节点ID不能为空")
    @ApiModelProperty(value = "种子节点ID", example = "", required = true)
    private String seedNodeId;

    @NotNull(message = "内部IP不能为空")
    @ApiModelProperty(value = "节点内网IP", example = "", required = true)
    private String internalIp;

    @NotNull(message = "内部端口不能为空")
    @ApiModelProperty(value = "节点内网端口", example = "20098", required = true)
    private Integer  internalPort;

}
