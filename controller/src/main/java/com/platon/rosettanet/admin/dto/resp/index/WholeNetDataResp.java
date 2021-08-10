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
public class WholeNetDataResp {

    @ApiModelProperty(value = "时间标识")
    private String timeFalg;

    @ApiModelProperty(value = "总量")
    private Long total;

}