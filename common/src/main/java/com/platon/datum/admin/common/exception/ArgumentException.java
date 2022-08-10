package com.platon.datum.admin.common.exception;

/**
 * @author liushuyu
 */
public class ArgumentException extends BizException {
    public ArgumentException() {
        super(Errors.ArgumentException);
    }

    public ArgumentException(String message) {
        super(Errors.ArgumentException, message);
    }
}