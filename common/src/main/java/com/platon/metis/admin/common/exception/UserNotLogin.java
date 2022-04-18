package com.platon.metis.admin.common.exception;

public class UserNotLogin extends BizException {
    public UserNotLogin() {
        super(Errors.UserNotLogin);
    }
}
