package com.platon.metis.admin.common.exception;

public class ObjectNotFound extends BizException {
    public ObjectNotFound() {
        super(Errors.ObjectNotFound.getCode(), Errors.ObjectNotFound.getMessage());
    }
}
