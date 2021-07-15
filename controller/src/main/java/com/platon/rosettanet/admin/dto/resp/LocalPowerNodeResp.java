package com.platon.rosettanet.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 计算节点详情返回参数
 */
@Data
@ApiModel(value = "计算节点详情返回参数")
public class LocalPowerNodeResp {

    @ApiModelProperty(value = "排序ID", example = "")
    private Integer id;

    @ApiModelProperty(value = "机构ID", example = "")
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

    @ApiModelProperty(value = "节点启用时间", example = "")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "节点备注", example = "")
    private String remarks;

    @ApiModelProperty(value = "节点(连接失败)信息", example = "")
    private String connMessage;

    @ApiModelProperty(value = "节点上一次连接时间", example = "")
    private LocalDateTime connTime;

    @ApiModelProperty(value = "节点状态，0:网络连接失败; 1:算力未启用(网络已连接); 2:算力已启用（空闲）; 3:算力已占用(计算服务算力正在被任务占用)", example = "")
    private String status;

    @ApiModelProperty(value = "计算host内存, 字节", example = "")
    private Long memory;

    @ApiModelProperty(value = "计算host core", example = "")
    private Integer core;

    @ApiModelProperty(value = "计算host带宽, bps", example = "")
    private Long bandwidth;

    @ApiModelProperty(value = "使用的内存, 字节", example = "")
    private Long usedMemory;

    @ApiModelProperty(value = "使用的core", example = "")
    private Integer usedCore;

    @ApiModelProperty(value = "使用的带宽, bps", example = "")
    private Long usedBandwidth;

    @ApiModelProperty(value = "创建时间", example = "")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后更新时间", example = "")
    private LocalDateTime updateTime;

}