package com.platon.rosettanet.admin.enums;

import lombok.Getter;

/**
 * @Author liushuyu
 * @Date 2021/4/14 17:14
 * @Version
 * 500
 * 通用异常
 * 通用异常，包含参数无效、缺失、系统运行时异常等情况
 *
 *
 * 4001
 * 系统异常
 * 服务内部系统异常
 *
 *
 * 3100
 * 签名验证失败
 * 请检查签名规则是否合法
 */
public enum ResponseCodeEnum {

    SUCCESS(0,"成功"),
    FAIL(500,"失败"),

    SIGN_VALID_ERROR(3100,"签名验证失败"),
    SYSTEM_ERROR(4001,"系统异常"),

    ;

    ResponseCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Getter
    private int code;
    @Getter
    private String message;
}
