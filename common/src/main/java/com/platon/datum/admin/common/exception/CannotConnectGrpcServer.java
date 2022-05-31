package com.platon.datum.admin.common.exception;

public class CannotConnectGrpcServer extends BizException {
    public CannotConnectGrpcServer() {
        super(Errors.CannotConnectGrpcServer);
    }
}