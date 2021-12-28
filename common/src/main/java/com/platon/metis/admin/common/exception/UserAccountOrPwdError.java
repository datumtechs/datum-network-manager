package com.platon.metis.admin.common.exception;

public class UserAccountOrPwdError extends BizException {
    public UserAccountOrPwdError() {
        super(Errors.UserAccountOrPwdError.getCode(), Errors.UserAccountOrPwdError.getMessage());
    }
}
