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
    UserAccountInvalid(1015, "user account invalid."),
    OrgInfoExists(1016, "org info already exists."),
    ObjectNotFound(1017, "object not found."),
    DuplicatedOperation(1018, "duplicated operation."),

    FileNameError(1019, "file name error."),
    FileEmpty(1020, "file is empty."),
    CannotDeletePublishedData(1021, "cannot delete published data."),
    CannotEditPublishedData(1022, "cannot edit published data."),
    CannotPublishData(1023, "cannot publish data."),
    CannotWithdrawData(1024, "cannot withdraw data."),

    CannotConnectPowerNode(1025, "cannot connect to power node."),
    CannotEditPowerNode(1026, "cannot edit power node."),
    PowerNodeNameExists(1027, "power node name exists."),


    SeedNodeExists(1028, "seed node exists."),
    CannotDeleteInitSeedNode(1029, "cannot delete init seed node."),


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