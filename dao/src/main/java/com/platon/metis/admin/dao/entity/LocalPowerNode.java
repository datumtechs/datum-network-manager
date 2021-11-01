package com.platon.metis.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 计算节点实体类
 */
@Data
@ApiModel(value = "本地计算节点")
public class LocalPowerNode {

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

    @ApiModelProperty(value = "开始时间", example = "")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "备注", example = "")
    private String remarks;

    @ApiModelProperty(value = "连接信息", example = "")
    private String connMessage;

    @ApiModelProperty(value = "算力id", example = "")
    private String powerId;

    @ApiModelProperty(value = " 算力状态 (0: 未知; 1: 还未发布的算力; 2: 已发布的算力(算力未被占用); 3: 已发布的算力(算力正在被占用); 4: 已撤销的算力)", example = "")
    private Integer powerStatus;

    @ApiModelProperty(value = "连接时间", example = "")
    private LocalDateTime connTime;

    @ApiModelProperty(value = "连接状态-1: 未被调度服务连接上; 0: 连接上; 1: 算力启用<计算服务>; 2: 算力被占用(计算服务算力正在被任务占用)")
    private Integer connStatus;

    public static enum ConnStatus {
        disconnected(0, "disconnected"),connected(1, "connected");
        private int code;
        private String value;

        ConnStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static ConnStatus codeOf(int code) {
            for (ConnStatus status : ConnStatus.values()) {
                if (status.ordinal()== code) {
                    return status;
                }
            }
            return null;
        }

    }

    @ApiModelProperty(value = "总内存")
    private Long memory;

    @ApiModelProperty(value = "总核心数")
    private Integer core;

    @ApiModelProperty(value = "总带宽")
    private Long bandwidth;

    @ApiModelProperty(value = "已使用内存")
    private Long usedMemory;

    @ApiModelProperty(value = "已使用核心数")
    private Integer usedCore;

    @ApiModelProperty(value = "已使用带宽")
    private Long usedBandwidth;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}