package com.platon.metis.admin.common.exception;

public abstract class BizException extends RuntimeException {
    private int errorCode = -1;
    private String message;

    public int getErrorCode() {
        return errorCode;
    }


    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public BizException(String msg) {
        super(msg);
        this.message = msg;
    }

    public BizException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.message = msg;
    }

    public BizException(Integer errorCode, String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());

        this.errorCode = errorCode;
        this.message = msg;
    }

}