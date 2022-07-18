package com.platon.datum.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "本地计算节点的负载快照")
public class PowerLoadSnapshot {
    @ApiModelProperty(value = "计算节点ID")
    private String nodeId;

    @ApiModelProperty(value = "时间点", notes = "UTC时间, 显示时要转成本地时间", example="2021-11-04T06:00:00")
    private LocalDateTime snapshotTime;

    @ApiModelProperty(value = "核心使用数")
    private Integer usedCore;

    @ApiModelProperty(value = "内存使用数")
    private Long usedMemory;

    @ApiModelProperty(value = "带宽使用数")
    private Long usedBandwidth;
}
