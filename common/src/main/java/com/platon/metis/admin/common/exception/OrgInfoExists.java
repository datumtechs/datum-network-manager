package com.platon.metis.admin.common.exception;

public class OrgInfoExists extends BizException {
    public OrgInfoExists() {
        super(Errors.OrgInfoExists.getCode(), Errors.OrgInfoExists.getMessage());
    }
}
