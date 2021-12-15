package com.platon.metis.admin.common.exception;

public enum Errors {

    SysException(1, "system internal error."),
    ArgumentException(2, "illegal argument."),
    CannotConnectGrpcServer(3, "connect gRPC server failed."),
    CallGrpcServiceFailed(4, "call gRPC service error"),


    UserNotLogin(1000, "user not login"),
    IdentityIdMissing(1001,"user identity not applied"),

    CarrierNotConfigured(1002, "carrier not configured."),
    CannotConnectCarrier(1003, "cannot connect to carrier."),
    CallCarrierFailed(1004, "call carrier service error."),
    OrgInfoNotFound(1005, "cannot find org info."),
    MetadataAuthorized(1006, "metadata authorized already."),
    DataHostExists(1007, "data host already exists."),
    PowerHostExists(1008, "power host already exists."),
    MetadataNotFound(1009, "metadata not found."),
    MetadataPublished(1010, "metadata is published."),
    MetadataNotPublished(1011, "metadata not published."),
    ImportEmptyFile(1012, "try to import a empty file."),
    ApplyIdentityIDFailed(1013, "apply identity id failed."),
    IdentityIDApplied(1014, "identity id applied already."),
    ;
    Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}