package com.platon.datum.admin.common.exception;

public class ApplyIdentityIDFailed extends BizException {
    public ApplyIdentityIDFailed() {
        super(Errors.ApplyIdentityIDFailed);
    }

    public ApplyIdentityIDFailed(String message) {
        super(Errors.ApplyIdentityIDFailed, message);
    }

    public ApplyIdentityIDFailed(Throwable ex) {
        super(Errors.ApplyIdentityIDFailed, ex);
    }
}
