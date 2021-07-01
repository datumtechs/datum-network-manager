package com.platon.rosettanet.admin.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Author liushuyu
 * @Date 2021/7/1 19:37
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class LogoutReq {

    @NotBlank(message = "userName不能为空")
    private String userName;//用户名
    @NotBlank(message = "userId不能为空")
    private String userId;//用户id
}
