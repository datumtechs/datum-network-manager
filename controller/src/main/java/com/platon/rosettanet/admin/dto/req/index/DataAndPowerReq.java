package com.platon.rosettanet.admin.dto.req.index;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 查询我发布的数据和算力请求参数
 * @author houz
 */
@Data
@ApiModel(value = "查询我发布的数据和算力请求参数")
public class DataAndPowerReq {

    @ApiModelProperty(value = "请求标志（1：数据，2：算力）", example = "1", required = true)
    @NotBlank(message = "请求标志不能为空")
    private String flag;

}
