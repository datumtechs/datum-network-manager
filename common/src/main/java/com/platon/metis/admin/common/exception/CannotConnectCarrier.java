package com.platon.metis.admin.common.exception;

public class CannotConnectCarrier extends BizException {
    public CannotConnectCarrier() {
        super(Errors.CannotConnectCarrier.getCode(), Errors.CannotConnectCarrier.getMessage());
    }
}