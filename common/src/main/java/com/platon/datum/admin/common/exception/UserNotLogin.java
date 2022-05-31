package com.platon.datum.admin.common.exception;

public class UserNotLogin extends BizException {
    public UserNotLogin() {
        super(Errors.UserNotLogin);
    }
}
