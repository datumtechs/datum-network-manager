package com.platon.metis.admin.common.exception;

public class CannotOpsPowerNode extends BizException {
    public CannotOpsPowerNode() {
        super(Errors.CannotOpsPowerNode);
    }

    public CannotOpsPowerNode(String message) {
        super(Errors.CannotOpsPowerNode, message);
    }
}
