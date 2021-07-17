package com.platon.rosettanet.admin.common.exception;

import lombok.Getter;

/**
 * @Author liushuyu
 * @Date 2021/4/15 16:04
 * @Version
 * @Desc
 */
public class ApplicationException extends RuntimeException{

    @Getter
    private ApplicationErrorEnum errorCode = ApplicationErrorEnum.COMMON;

    @Getter
    private String errorMsg;

    public ApplicationException(String errorMsg){
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public ApplicationException(String errorMsg,ApplicationErrorEnum errorCode){
        this(errorMsg);
        this.errorCode = errorCode;
    }

    public ApplicationException(Throwable exception){
        super(exception);
        this.errorMsg = exception.getMessage();
    }

    public ApplicationException(Throwable exception,ApplicationErrorEnum errorCode){
        this(exception);
        this.errorCode = errorCode;
    }

    public ApplicationException(String errorMsg, Throwable exception){
        super(exception);
        this.errorMsg = errorMsg;
    }

    public ApplicationException(String errorMsg, Throwable exception,ApplicationErrorEnum errorCode){
        this(errorMsg,exception);
        this.errorCode = errorCode;
    }

    @Getter
    public enum ApplicationErrorEnum {

        COMMON("系统内部异常"),
        IDENTITY_ID_MISSING("使用平台功能需要申请身份标识，需要申请身份标识"),
        CARRIER_INFO_NOT_CONFIGURED("无可用的调度服务"),
        ;

        ApplicationErrorEnum(String desc){
            this.desc = desc;
        }

        private String desc;
    }
}
