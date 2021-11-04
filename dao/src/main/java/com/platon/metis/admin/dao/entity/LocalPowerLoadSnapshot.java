package com.platon.metis.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "本地计算节点的负载快照")
public class LocalPowerLoadSnapshot {
    @ApiModelProperty(value = "时间点", notes = "UTC时间, 显示时要转成本地时间", example="2021-11-04T06:00:00")
    private LocalDateTime snapshotTime;

    @ApiModelProperty(value = "核心负载百分比")
    private Integer corePct;

    @ApiModelProperty(value = "内存负载百分比")
    private Integer memoryPct;

    @ApiModelProperty(value = "带宽负载百分比")
    private Integer bandwidthPct;
}
