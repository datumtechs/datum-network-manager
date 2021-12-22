package com.platon.metis.admin.common.exception;

public class CannotWithdrawData extends BizException {
    public CannotWithdrawData() {
        super(Errors.CannotWithdrawData.getCode(), Errors.CannotWithdrawData.getMessage());
    }
}
