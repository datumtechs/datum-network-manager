package com.platon.datum.admin.common.exception;

public enum Errors {
    SUCCESS(0, "success."),
    SysException(1, "system internal error."),
    ArgumentException(2, "illegal argument."),
    CannotConnectGrpcServer(3, "connect gRPC server failed."),
    CallGrpcServiceFailed(4, "call gRPC service error"),
    MethodParamInvalid(5,"method argument invalid!"),
    SQLERROR(6,"SQL error!"),

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
    UserAccountOrPwdError(1016, "user account or password error."),
    VerificationCodeError(1017, "verification code error."),
    OrgInfoExists(1018, "org info already exists."),
    ObjectNotFound(1019, "object not found."),
    DuplicatedOperation(1020, "duplicated operation."),

    FileNameError(1021, "file name error."),
    FileEmpty(1022, "file is empty."),
    CannotDeletePublishedData(1023, "cannot delete published data."),
    CannotEditPublishedData(1024, "cannot edit published data."),
    CannotPublishData(1025, "cannot publish data."),
    CannotWithdrawData(1026, "cannot withdraw data."),

    CannotConnectPowerNode(1027, "cannot connect to power node."),
    CannotEditPowerNode(1028, "cannot edit power node."),
    PowerNodeNameExists(1029, "power node name exists."),


    SeedNodeExists(1030, "seed node exists."),
    CannotDeleteInitSeedNode(1031, "cannot delete init seed node."),
    MetadataResourceNameExists(1032, "metadata resource name exists."),
    MetadataResourceNameIllegal(1033, "metadata resource name illegal."),

    NodeNameExists(1034, "node name exists."),
    NodeNameIllegal(1035, "node name illegal."),

    OrgConnectNetworkAlready(1036, "local org connect network already."),
    OrgNotConnectNetwork(1037, "local org not connect network."),

    CannotOpsPowerNode(1038, "cannot manipulate power node in publishing and revoking,please try again later."),
    CannotOpsData(1039, "cannot manipulate data in publishing and revoking,please try again later."),

    NonceExpired(1040,"Please get nonce again. Nonce has expired!"),
    NonceIncorrect(1041,"Please get nonce again. Nonce is incorrect!"),

    UserLoginSignError(1042,"User login signature error!"),

    ExportCSVFileError(1043,"Export csv file error!"),
    ReadFileContentError(1044,"Read file content error!"),

    DataTokenInPublishing(1045,"Metadata already exists token in publishing!"),
    DataTokenExists(1046,"Metadata already exists token!"),

    CannotChangeSystemConfig(1047,"The system configuration is not allowed to be changed!"),

    CreateNewUserFailed(1048,"Insert new user failed!"),
    CurrentUserNotAdmin(1049,"The current user is not an administrator, the replacement failed!"),
    NewUserAlreadyAdmin(1050,"The new user already administrator, the replacement failed!"),

    OrgInNetwork(1051,"Organization information cannot be modified because the organization has not been removed from the network!"),
    UserNoPermission(1052,"The current user does not have permission!"),

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