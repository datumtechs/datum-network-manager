package com.platon.metis.admin.common.exception;

public class SysException extends BizException {
    public SysException() {
        super(Errors.SysException.getCode(), Errors.SysException.getMessage());
    }

    public SysException(String message) {
        super(Errors.SysException.getCode(), message);
    }

    public SysException(String message, Throwable t) {
        super(Errors.SysException.getCode(), message, t);
    }
}
