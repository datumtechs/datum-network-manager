package com.platon.datum.admin.dto.resp;

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

    @ApiModelProperty(value = "任务状态(0:unknown未知、1:pending等在中、2:running计算中、3:failed失败、4:success成功)")
    private Integer status;

    @ApiModelProperty(value = "状态数量")
    private Integer statusCount;

}