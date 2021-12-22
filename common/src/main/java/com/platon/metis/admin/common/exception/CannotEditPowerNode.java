package com.platon.metis.admin.common.exception;

public class CannotEditPowerNode extends BizException {
    public CannotEditPowerNode() {
        super(Errors.CannotEditPowerNode.getCode(), Errors.CannotEditPowerNode.getMessage());
    }
}
