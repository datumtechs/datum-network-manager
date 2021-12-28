package com.platon.metis.admin.common.exception;

public class VerificationCodeError extends BizException {
    public VerificationCodeError() {
        super(Errors.VerificationCodeError.getCode(), Errors.VerificationCodeError.getMessage());
    }
}
