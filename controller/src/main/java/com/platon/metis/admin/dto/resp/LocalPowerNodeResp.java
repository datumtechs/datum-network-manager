package com.platon.metis.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 计算节点返回详情
 */
@Data
@ApiModel(value = "计算节点返回详情")
public class LocalPowerNodeResp {

    @ApiModelProperty(value = "序号id", example = "")
    private Integer id;

    @ApiModelProperty(value = "组织id", example = "")
    private String identityId;

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

    @ApiModelProperty(value = "开始时间", example = "")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "备注", example = "")
    private String remarks;

    @ApiModelProperty(value = "连接信息", example = "")
    private String connMessage;

    @ApiModelProperty(value = "算力id", example = "")
    private String powerId;

    @ApiModelProperty(value = "连接时间", example = "")
    private LocalDateTime connTime;

    @ApiModelProperty(value = "总内存", example = "")
    private Long memory;

    @ApiModelProperty(value = "总cpu", example = "")
    private Integer core;

    @ApiModelProperty(value = "总带宽", example = "")
    private Long bandwidth;

    @ApiModelProperty(value = "已使用内存", example = "")
    private Long usedMemory;

    @ApiModelProperty(value = "已使用cpu", example = "")
    private Integer usedCore;

    @ApiModelProperty(value = "已使用带宽", example = "")
    private Long usedBandwidth;

    @ApiModelProperty(value = "创建时间", example = "")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "")
    private LocalDateTime updateTime;

}