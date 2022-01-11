package com.platon.metis.admin.common.exception;

public class OrgConnectNetworkAlready extends BizException {
    public OrgConnectNetworkAlready() {
        super(Errors.OrgConnectNetworkAlready.getCode(), Errors.OrgConnectNetworkAlready.getMessage());
    }
}
