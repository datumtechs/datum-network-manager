package com.platon.rosettanet.admin.dto.resp.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 
 * VIEW
 */
@Data
@ApiModel(value = "我的计算任务概况响应")
public class MyTaskStatsResp {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "状态数量")
    private Integer statusCount;

}