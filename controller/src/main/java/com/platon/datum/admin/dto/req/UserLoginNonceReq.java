package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/3/16 1:41
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel("获取nonce")
public class UserLoginNonceReq {
    @ApiModelProperty(value = "用户钱包地址", required = true, notes = "address", example = "501eb3eeb2a40e6f2ff6f481302435e6e8af3666")
    private String address;
}
