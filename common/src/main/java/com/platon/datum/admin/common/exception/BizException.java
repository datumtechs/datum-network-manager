package com.platon.datum.admin.common.exception;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BizException extends RuntimeException {
    private int errorCode;
    private String message;


    /**
     * @param errors 返回给前端的响应码和响应信息
     */
    public BizException(Errors errors) {
        super(errors.getMessage());
        this.errorCode = errors.getCode();
        this.message = errors.getMessage();
    }

    /**
     * @param errors 返回给前端的响应码和响应信息
     * @param msg 打印在日志中的异常信息
     */
    public BizException(Errors errors, String msg) {
        super(msg);
        this.errorCode = errors.getCode();
        this.message = errors.getMessage();
    }

    /**
     * @param errors 返回给前端的响应码和响应信息
     * @param t 打印在日志中的异常信息
     */
    public BizException(Errors errors, Throwable t) {
        super(t);
        this.errorCode = errors.getCode();
        this.message = errors.getMessage();
    }

}