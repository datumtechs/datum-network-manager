package com.platon.rosettanet.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

/**
 * @Author liushuyu
 * @Date 2021/7/1 19:36
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class UserLoginReq {

    @NotBlank(message = "userName不能为空")
    @ApiModelProperty(value = "用户名",required = true)
    private String userName;//用户名
    @NotBlank(message = "passwd不能为空")
    @ApiModelProperty(value = "密码",required = true)
    private String passwd;//登陆密码
    @ApiModelProperty(value = "验证码",required = false)
    private String code;//登陆验证码
}
