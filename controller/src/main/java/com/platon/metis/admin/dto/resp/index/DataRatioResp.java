package com.platon.metis.admin.dto.resp.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 
 * VIEW
 */
@Data
@ApiModel(value = "全网数据环比响应")
public class DataRatioResp {

    @ApiModelProperty(value = "月环比")
    private String ringRatio;

    @ApiModelProperty(value = "月同比")
    private String sameRatio;

}