package com.platon.metis.admin.common.exception;

public class DuplicatedOperation extends BizException {
    public DuplicatedOperation() {
        super(Errors.DuplicatedOperation.getCode(), Errors.DuplicatedOperation.getMessage());
    }
}
