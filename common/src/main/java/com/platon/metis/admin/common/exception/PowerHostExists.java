package com.platon.metis.admin.common.exception;

public class PowerHostExists extends BizException {
    public PowerHostExists() {
        super(Errors.PowerHostExists.getCode(), Errors.PowerHostExists.getMessage());
    }
}
