package com.platon.metis.admin.dto.resp.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 *授权数据统计
 */

@Data
@ApiModel(value = "授权数据数据统计响应")
public class LocalDataAuthStatisticsResp {

    @ApiModelProperty(name = "finishAuthCount", value = "已授权数据数量")
    private int finishAuthCount;
    @ApiModelProperty(name = "unFinishAuthCount", value = "未授权数据数量")
    private int unFinishAuthCount;
}
