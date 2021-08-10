package com.platon.rosettanet.admin.dto.req.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author houz
 * 全网数据走势请求参数
 */
@Data
@ApiModel(value = "全网数据走势请求参数")
public class WholeNetDataReq {

    @ApiModelProperty(value = "请求标志（1：数据，2：数据）", example = "", required = true)
    private String flag;
}
