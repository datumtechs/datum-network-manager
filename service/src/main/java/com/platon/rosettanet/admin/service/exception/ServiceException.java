package com.platon.rosettanet.admin.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
