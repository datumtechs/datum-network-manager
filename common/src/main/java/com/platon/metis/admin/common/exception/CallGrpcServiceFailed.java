package com.platon.metis.admin.common.exception;

public class CallGrpcServiceFailed extends BizException {
    public CallGrpcServiceFailed() {
        super(Errors.CallGrpcServiceFailed);
    }
    public CallGrpcServiceFailed(String msg) {
        super(Errors.CallGrpcServiceFailed, msg);
    }
}