package com.platon.rosettanet.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author houz
 * 计算节点详情返回参数
 */
@Data
@ApiModel(value = "计算节点详情返回参数")
public class LocalPowerNodeResp {

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

    @ApiModelProperty(value = "节点状态，-1:网络连接失败; 0:连接上; 1:算力未启用(网络已连接); " +
            "2:算力已启用（空闲）; 3:算力已占用(计算服务算力正在被任务占用)", example = "")
    private String status;

}