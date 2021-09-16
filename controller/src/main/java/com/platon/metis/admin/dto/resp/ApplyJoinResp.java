package com.platon.metis.admin.dto.resp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * 入网退网返回参数
 */
@Data
@ApiModel(value = "入网返回参数")
public class ApplyJoinResp {

    @ApiModelProperty(value = "入网状态 0未入网，1已入网")
    private Integer status;


}
