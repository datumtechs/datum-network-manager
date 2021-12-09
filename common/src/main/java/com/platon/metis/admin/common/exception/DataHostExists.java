package com.platon.metis.admin.common.exception;

public class DataHostExists extends BizException {
    public DataHostExists() {
        super(Errors.DataHostExists.getCode(), Errors.DataHostExists.getMessage());
    }
}
