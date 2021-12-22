package com.platon.metis.admin.common.exception;

public class CannotConnectPowerNode extends BizException {
    public CannotConnectPowerNode() {
        super(Errors.CannotConnectPowerNode.getCode(), Errors.CannotConnectPowerNode.getMessage());
    }
}
