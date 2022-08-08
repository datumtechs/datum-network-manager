package com.platon.datum.admin.common.exception;

public class CallGrpcServiceFailed extends BizException {
    public CallGrpcServiceFailed() {
        super(Errors.CallGrpcServiceFailed);
    }
    public CallGrpcServiceFailed(String msg) {
        super(Errors.CallGrpcServiceFailed, msg);
    }

    public CallGrpcServiceFailed(Throwable t) {
        super(Errors.CallGrpcServiceFailed, t);
    }
}