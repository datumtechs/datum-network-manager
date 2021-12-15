package com.platon.metis.admin.common.exception;

public class SysException extends BizException{
    public SysException() {
        super(Errors.SysException.getCode(), Errors.SysException.getMessage());
    }
}
