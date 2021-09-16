package com.platon.metis.admin.service.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author liushuyu
 * @Date 2021/4/15 16:04
 * @Version
 * @Desc
 */
@Data
@NoArgsConstructor
public class ServiceException extends RuntimeException{

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
