package com.platon.rosettanet.admin.dto.req;

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
public class LoginReq {

    @NotBlank(message = "userName不能为空")
    private String userName;//用户名
    @NotBlank(message = "passwd不能为空")
    private String passwd;//登陆密码
    private String code;//登陆验证码
}
