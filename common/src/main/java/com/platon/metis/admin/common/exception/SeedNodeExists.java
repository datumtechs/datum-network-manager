package com.platon.metis.admin.common.exception;

public class SeedNodeExists  extends BizException {
    public SeedNodeExists() {
        super(Errors.SeedNodeExists.getCode(), Errors.SeedNodeExists.getMessage());
    }
}
