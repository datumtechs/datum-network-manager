package com.platon.metis.admin.dto.req.seed;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houzhuang
 * 新增种子节点请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "新增种子节点请求参数")
public class SeedAddReq {

    @NotNull(message = "种子节点名称不能为空")
    @ApiModelProperty(value = "种子节点名称", example = "", required = true)
    private String seedNodeName;

    @NotNull(message = "节点id不能为空")
    @ApiModelProperty(value = "节点ID", example = "", required = true)
    private String nodeId;

    @NotNull(message = "内部IP不能为空")
    @ApiModelProperty(value = "节点内网IP", example = "", required = true)
    private String internalIp;

    @NotNull(message = "内部端口不能为空")
    @ApiModelProperty(value = "节点内网端口", example = "", required = true)
    private Integer  internalPort;

}
