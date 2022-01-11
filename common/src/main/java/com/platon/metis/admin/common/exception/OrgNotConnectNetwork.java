package com.platon.metis.admin.common.exception;

public class OrgNotConnectNetwork extends BizException {
    public OrgNotConnectNetwork() {
        super(Errors.OrgNotConnectNetwork.getCode(), Errors.OrgNotConnectNetwork.getMessage());
    }
}
