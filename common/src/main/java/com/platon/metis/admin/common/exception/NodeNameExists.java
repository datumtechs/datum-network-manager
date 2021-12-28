package com.platon.metis.admin.common.exception;

public class NodeNameExists extends BizException {
    public NodeNameExists() {
        super(Errors.NodeNameExists.getCode(), Errors.NodeNameExists.getMessage());
    }
}
