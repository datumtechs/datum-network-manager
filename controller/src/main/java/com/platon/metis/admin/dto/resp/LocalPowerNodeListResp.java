package com.platon.metis.admin.dto.resp;

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

    @ApiModelProperty(value = "节点与调度服务的连接状态（0: 未被调度服务连接上; 1: 连接上）", example = "")
    private Integer connStatus;

    @ApiModelProperty(value = "算力状态 (0:未知、1:未启用、2:空闲(已启用)、3:占用(已启用)、4:已撤销", example = "")
    private Integer powerStatus;

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