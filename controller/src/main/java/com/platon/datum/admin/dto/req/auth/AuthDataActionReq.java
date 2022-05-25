package com.platon.datum.admin.dto.req.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 数据授权操作Req
 */


@Data
@ApiModel(value = "数据授权操作Req")
public class AuthDataActionReq {
    //授权数据Id
    @NotNull(message = "数据授权申请Id不能为空")
    @ApiModelProperty(value = "数据授权申请Id",required = true)
    private String authId;
    //授权数据动作 (1: 同意; 2: 拒绝;)
    @NotNull(message = "授权数据action不能为空")
    @ApiModelProperty(value = "授权数据动作 (1: 同意; 2: 拒绝;)",required = true)
    private Integer action;
}
