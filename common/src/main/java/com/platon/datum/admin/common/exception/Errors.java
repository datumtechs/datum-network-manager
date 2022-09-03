package com.platon.datum.admin.common.exception;

public enum Errors {
    SUCCESS(0, "Success."),
    SysException(1, "System internal error."),
    ArgumentException(2, "Illegal argument."),
    CannotConnectGrpcServer(3, "Connect gRPC server failed."),
    CallGrpcServiceFailed(4, "Call gRPC service error"),
    MethodParamInvalid(5, "Method argument invalid!"),
    SqlError(6, "SQL error!"),
    QueryRecordNotExist(7, "Query record is not exist!"),
    InsertSqlFailed(8, "Insert failed!"),
    UpdateSqlFailed(9, "Update failed!"),
    DeleteSqlEFailed(10, "Delete failed!"),

    UserNotLogin(1000, "User not login"),
    IdentityIdMissing(1001, "User identity not applied"),

    CarrierNotConfigured(1002, "Carrier not configured."),
    CannotConnectCarrier(1003, "Cannot connect to carrier."),
    CallCarrierFailed(1004, "Call carrier service error."),
    OrgInfoNotFound(1005, "Cannot find org info."),
    MetadataAuthorized(1006, "Metadata authorized already."),
    DataHostExists(1007, "Data host already exists."),
    PowerHostExists(1008, "Power host already exists."),
    MetadataNotFound(1009, "Metadata not found."),
    MetadataPublished(1010, "Metadata is published."),
    MetadataNotPublished(1011, "Metadata not published."),
    ImportEmptyFile(1012, "Try to import a empty file."),
    ApplyIdentityIDFailed(1013, "Apply identity id failed."),
    IdentityIDApplied(1014, "Identity id applied already."),
    UserAccountInvalid(1015, "User account invalid."),
    UserAccountOrPwdError(1016, "User account or password error."),
    VerificationCodeError(1017, "Verification code error."),
    OrgInfoExists(1018, "Org info already exists."),
    ObjectNotExist(1019, "Object not exist."),
    DuplicatedOperation(1020, "Duplicated operation."),

    FileNameError(1021, "File name error."),
    FileEmpty(1022, "File is empty."),
    CannotDeletePublishedData(1023, "Cannot delete published data."),
    CannotEditPublishedData(1024, "Cannot edit published data."),
    CannotPublishData(1025, "Cannot publish data."),
    CannotWithdrawData(1026, "Cannot withdraw data."),

    CannotConnectPowerNode(1027, "Cannot connect to power node."),
    CannotEditPowerNode(1028, "Cannot edit power node."),
    PowerNodeNameExists(1029, "Power node name exists."),


    SeedNodeExists(1030, "Seed node exists."),
    CannotDeleteInitSeedNode(1031, "Cannot delete init seed node."),
    MetadataResourceNameExists(1032, "Metadata resource name exists."),
    MetadataResourceNameIllegal(1033, "Metadata resource name illegal."),

    NodeNameExists(1034, "Node name exists."),
    NodeNameIllegal(1035, "Node name illegal."),

    OrgConnectNetworkAlready(1036, "Local org connect network already."),
    OrgNotConnectNetwork(1037, "Local org not connect network."),

    CannotOpsPowerNode(1038, "Cannot manipulate power node in publishing and revoking,please try again later."),
    CannotOpsData(1039, "Cannot manipulate data in publishing and revoking,please try again later."),

    NonceExpired(1040, "Please get nonce again. Nonce has expired!"),
    NonceIncorrect(1041, "Please get nonce again. Nonce is incorrect!"),

    UserLoginSignError(1042, "User login signature error!"),

    ExportFileError(1043, "Export file error!"),
    ReadFileContentError(1044, "Read file content error!"),

    DataTokenInPublishing(1045, "Metadata already exists token in publishing!"),
    DataTokenExists(1046, "Metadata already exists token!"),

    CannotChangeSystemConfig(1047, "The system configuration is not allowed to be changed!"),

    CreateNewUserFailed(1048, "Insert new user failed!"),
    CurrentUserNotAdmin(1049, "The current user is not an administrator, the replacement failed!"),
    NewUserAlreadyAdmin(1050, "The new user already administrator, the replacement failed!"),

    OrgInNetwork(1051, "Organization information cannot be modified because the organization has not been removed from the network!"),
    UserNoPermission(1052, "The current user does not have permission!"),
    InsufficientWalletBalance(1053, "Insufficient wallet balance!"),
    ValidateFailed(1054, "Validate failed!"),
    UploadFileFailed(1055, "upload file failed!"),
    ApplyRecordIsApplying(1056, "Apply record is applying!"),
    ApplyRecordIsRejected(1057, "Apply record is rejected!"),
    VcIsInvalid(1058, "Vc is invalid!"),
    UsedVcIsExist(1059, "Used vc is exist!"),
    DownloadFailed(1060, "Download failed!"),
    NonceTooLow(1061, "Nonce too low!"),
    TxExceedsBlockGasLimit(1062, "Exceeds block gas limit!"),
    TxKnownTx(1063, "known transaction"),
    TimeLessThan24H(1064, "Time is less than 24 hours!"),
    YouAreNotOwner(1065, "You are not owner!"),
    AlreadyEffectProposal(1066, "Proposal already effect!"),
    ProposalRevokeTimeExpired(1067, "Revoke time has passed!"),


    AuthorityNotExist(1068, "Authority not exist!"),
    VcAlreadyExists(1069, "The vc already exists！"),
    ApplyingVcAlreadyExists(1070, "A applying vc already exists！"),
    ProposalStatusNotStartVote(1071, "Proposal status is not 'start vote' status!"),
    AuthorityAlreadyExists(1072, "Authority already exist!"),
    AuthorityAlreadyKickOut(1073, "Authority already has been kicked out!"),
    CantKickOutAuthorityAdmin(1074, "Can't kick out authority admin!"),
    AuthorityAdminCantExit(1075, "Authority admin can't exit!"),
    AnOpenProposalAlreadyExists(1076, "An open proposal already exists!"),
    AuthorityCantExitNetwork(1077, "Authority can't exit network!"),


    // rpc调用时异常
    CallRpcError(2000, "Call node rpc exception"),
    CallRpcNetError(2001, "Call node rpc network exception"),
    CallRpcBizError(2002, "Call node rpc business exception【%s】"),
    CallRpcReadTimeout(2003, "Call node rpc read timeout"),
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