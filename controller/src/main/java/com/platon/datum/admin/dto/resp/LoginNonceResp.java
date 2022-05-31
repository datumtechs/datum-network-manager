package com.platon.datum.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/3/16 2:04
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel("获取登录nonce参数")
public class LoginNonceResp {

    @ApiModelProperty("登录随机数")
    private String nonce;
}
