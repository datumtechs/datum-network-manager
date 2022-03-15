package com.platon.metis.admin.dto.req;

import com.platon.metis.admin.common.annotation.CheckAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/3/16 4:02
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel("获取nonce")
public class UserUpdateAdminReq {

    @ApiModelProperty(value = "新的用户钱包地址", required = true, notes = "newAddress", example = "501eb3eeb2a40e6f2ff6f481302435e6e8af3666")
    @CheckAddress(message = "{user.address.format}")
    private String newAddress;
}
