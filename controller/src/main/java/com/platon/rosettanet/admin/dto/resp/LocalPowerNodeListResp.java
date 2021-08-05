package com.platon.rosettanet.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author houz
 * 计算节点返回列表
 */
@Data
@ApiModel(value = "计算节点返回列表")
public class LocalPowerNodeListResp {

    @ApiModelProperty(value = "计算节点ID", example = "")
    private String powerNodeId;

    @ApiModelProperty(value = "计算节点名称", example = "")
    private String powerNodeName;

    @ApiModelProperty(value = "节点内网IP", example = "")
    private String internalIp;

    @ApiModelProperty(value = "节点内网端口", example = "")
    private Integer internalPort;

    @ApiModelProperty(value = "节点外网IP", example = "")
    private String externalIp;

    @ApiModelProperty(value = "节点外网端口", example = "")
    private Integer externalPort;

    @ApiModelProperty(value = "节点状态，-1: 未被调度服务连接上; 0: 连接上; 1: 算力启用<计算服务>; 2: 算力被占用(计算服务算力正在被任务占用)", example = "")
    private String connStatus;

    @ApiModelProperty(value = "算力ID(停用算力时使用的id)", example = "")
    private String powerId;

    @ApiModelProperty(value = "内存", example = "")
    private Long memory;

    @ApiModelProperty(value = "cpu", example = "")
    private Integer core;

    @ApiModelProperty(value = "带宽", example = "")
    private Long bandwidth;

    @ApiModelProperty(value = "备注", example = "")
    private String remarks;

}