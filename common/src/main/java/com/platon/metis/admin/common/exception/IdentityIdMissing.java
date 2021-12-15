package com.platon.metis.admin.common.exception;

public class IdentityIdMissing extends BizException {
    public IdentityIdMissing() {
        super(Errors.IdentityIdMissing.getCode(), Errors.IdentityIdMissing.getMessage());
    }
}
