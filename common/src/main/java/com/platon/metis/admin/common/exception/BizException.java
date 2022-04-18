package com.platon.metis.admin.common.exception;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BizException extends RuntimeException {
    private int errorCode;
    private String message;

    public BizException(Errors errors) {
        super(errors.getMessage());
        this.errorCode = errors.getCode();
        this.message = errors.getMessage();
    }

    public BizException(Errors errors, String msg) {
        super(msg);
        this.errorCode = errors.getCode();
        this.message = errors.getMessage();
    }

    public BizException(Errors errors, Throwable t) {
        super(errors.getMessage(), t);
        this.errorCode = errors.getCode();
        this.message = errors.getMessage();
    }

}