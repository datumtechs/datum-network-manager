package com.platon.datum.admin.common.exception;

public class ValidateException extends BizException {
    public ValidateException() {
        super(Errors.ValidateFailed);
    }

    public ValidateException(String message) {
        super(Errors.ValidateFailed,message);
    }
}