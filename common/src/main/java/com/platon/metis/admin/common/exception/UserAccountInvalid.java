package com.platon.metis.admin.common.exception;

public class UserAccountInvalid extends BizException {
    public UserAccountInvalid() {
        super(Errors.UserAccountInvalid.getCode(), Errors.UserAccountInvalid.getMessage());
    }
}
