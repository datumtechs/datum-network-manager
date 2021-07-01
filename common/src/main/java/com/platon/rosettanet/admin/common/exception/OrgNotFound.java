package com.platon.rosettanet.admin.common.exception;

public class OrgNotFound extends BizException {
    private final static int CODE = 1003;
    private final static String MESSAGE = "Organization not found.";

    public OrgNotFound() {
        super(CODE, MESSAGE);
    }

    public OrgNotFound(String msg) {
        super(CODE, msg);
    }
}
