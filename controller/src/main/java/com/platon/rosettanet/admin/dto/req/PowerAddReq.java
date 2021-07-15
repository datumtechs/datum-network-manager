package com.platon.rosettanet.admin.dto.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author houzhuang
 * 新增计算节点请求参数
 */
@Data
@ApiModel(value = "新增计算节点请求参数")
public class PowerAddReq {

    @NotNull(message = "计算节点名称不能为空")
    @ApiModelProperty(value = "计算节点名称", example = "计算节点5", required = true)
    private String powerNodeName;

    @NotNull(message = "内部IP不能为空")
    @ApiModelProperty(value = "节点内网IP", example = "10.2.2.210", required = true)
    private String internalIp;

    @NotNull(message = "外部IP不能为空")
    @ApiModelProperty(value = "节点外网IP", example = "10.2.2.210", required = true)
    private String externalIp;

    @NotNull(message = "内部端口不能为空")
    @ApiModelProperty(value = "节点内网端口", example = "20098", required = true)
    private Integer  internalPort;

    @NotNull(message = "外部端口不能为空")
    @ApiModelProperty(value = "节点外网端口", example = "20093", required = true)
    private Integer externalPort;

    @ApiModelProperty(value = "节点备注", example = "备注了吧", required = false)
    private String remarks;


}
