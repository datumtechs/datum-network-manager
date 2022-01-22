package com.platon.metis.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 计算节点参与的正在执行的任务信息，这个表记录的数据比较简单，特别是其它参与方，都作为协作方合并处理了。结果接收方也是合并处理了。
 * todo: 表名改成local_power_running_task
 */
@Data
@ApiModel(value = "计算节点参与的正在执行的任务")
public class LocalPowerJoinTask {  //todo: 类名改成localPowerRunningTask
    @ApiModelProperty(value = "序号ID")
    private Integer id;

    @ApiModelProperty(value = "计算节点ID")
    private String nodeId;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务发起方组织id")
    private String ownerIdentityId;

    @ApiModelProperty(value = "任务发起方组织名称")
    private String ownerIdentityName;

    @ApiModelProperty(value = "发起时间")
    private LocalDateTime taskStartTime;

    @ApiModelProperty(value = "结果接收方组织名称")
    private String resultSideName;

    @ApiModelProperty(value = "结果接收方组织id")
    private String resultSideId;

    @ApiModelProperty(value = "协作方组织id")
    private String coordinateSideId;

    @ApiModelProperty(value = "协作方组织名称")
    private String coordinateSideName;

    @ApiModelProperty(value = "使用的内存")
    private Long usedMemory;

    @ApiModelProperty(value = "使用的核心")
    private Integer usedCore;

    @ApiModelProperty(value = "使用的带宽")
    private Long usedBandwidth;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}