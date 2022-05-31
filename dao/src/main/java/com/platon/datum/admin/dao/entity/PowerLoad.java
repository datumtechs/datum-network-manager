package com.platon.datum.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "计算节点当前的负载")
public class PowerLoad {
    @ApiModelProperty(value = "核心负载百分比，仅指%前的值，没有小数")
    private Integer corePct;

    @ApiModelProperty(value = "内存负载百分比，仅指%前的值，没有小数")
    private Integer memoryPct;

    @ApiModelProperty(value = "带宽负载百分比，仅指%前的值，没有小数")
    private Integer bandwidthPct;
}
