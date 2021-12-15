package com.platon.metis.admin.common.exception;

public class CannotConnectGrpcServer extends BizException {
    public CannotConnectGrpcServer() {
        super(Errors.CannotConnectGrpcServer.getCode(), Errors.CannotConnectGrpcServer.getMessage());
    }
}