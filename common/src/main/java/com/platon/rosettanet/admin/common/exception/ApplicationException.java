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
    private String errorMsg;

    public ApplicationException(String errorMsg){
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public ApplicationException(Throwable exception){
        super(exception);
    }

    public ApplicationException(String errorMsg, Throwable exception){
        super(exception);
        this.errorMsg = errorMsg;
    }
}
