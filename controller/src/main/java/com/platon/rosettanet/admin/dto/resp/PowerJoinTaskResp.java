package com.platon.rosettanet.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 计算节点实体类
 */
@Data
@ApiModel(value = "计算节点参与任务返回列表")
public class PowerJoinTaskResp {

    @ApiModelProperty(value = "序号ID")
    private Integer id;

    @ApiModelProperty(value = "计算节点ID")
    private String powerNodeId;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "发起方身份")
    private String ownerIdentityId;

    @ApiModelProperty(value = "发起时间")
    private LocalDateTime taskStartTime;

    @ApiModelProperty(value = "结果方")
    private String resultSide;

    @ApiModelProperty(value = "协作方")
    private String coordinateSide;

    @ApiModelProperty(value = "使用的内存")
    private Long usedMemory;

    @ApiModelProperty(value = "使用的core")
    private Integer usedCore;

    @ApiModelProperty(value = "使用的带宽")
    private Long usedBandwidth;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;

}