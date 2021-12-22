package com.platon.metis.admin.common.exception;

public class PowerNodeNameExists extends BizException {
    public PowerNodeNameExists() {
        super(Errors.PowerNodeNameExists.getCode(), Errors.PowerNodeNameExists.getMessage());
    }
}
