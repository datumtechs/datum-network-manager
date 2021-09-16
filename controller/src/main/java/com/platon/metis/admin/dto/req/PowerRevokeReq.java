package com.platon.metis.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houzhuang
 * 停用算力请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "停用算力请求参数")
public class PowerRevokeReq {

    @NotNull(message = "算力ID不能为空")
    @ApiModelProperty(value = "算力ID", example = "", required = true)
    private String powerId;

}
