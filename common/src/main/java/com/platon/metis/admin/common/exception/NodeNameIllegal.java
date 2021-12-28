package com.platon.metis.admin.common.exception;

public class NodeNameIllegal extends BizException {
    public NodeNameIllegal() {
        super(Errors.NodeNameIllegal.getCode(), Errors.NodeNameIllegal.getMessage());
    }
}
