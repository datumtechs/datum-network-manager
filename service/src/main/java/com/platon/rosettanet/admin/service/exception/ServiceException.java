package com.platon.rosettanet.admin.service.exception;

import lombok.Getter;

/**
 * @Author liushuyu
 * @Date 2021/4/15 16:04
 * @Version
 * @Desc
 */
public class ServiceException extends RuntimeException{

    @Getter
    private String errorMsg;

    public ServiceException(String errorMsg){
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public ServiceException(Throwable exception){
        super(exception);
    }

    public ServiceException(String errorMsg, Throwable exception){
        super(exception);
        this.errorMsg = errorMsg;
    }
}
