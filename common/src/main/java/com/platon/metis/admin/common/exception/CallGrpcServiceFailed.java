package com.platon.metis.admin.common.exception;

public class CallGrpcServiceFailed extends BizException {
    public CallGrpcServiceFailed() {
        super(Errors.CallGrpcServiceFailed.getCode(), Errors.CallGrpcServiceFailed.getMessage());
    }
    public CallGrpcServiceFailed(String msg) {
        super(msg);
    }
}
