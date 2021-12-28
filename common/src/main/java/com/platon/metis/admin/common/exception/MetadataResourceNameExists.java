package com.platon.metis.admin.common.exception;

public class MetadataResourceNameExists extends BizException {
    public MetadataResourceNameExists() {
        super(Errors.MetadataResourceNameExists.getCode(), Errors.MetadataResourceNameExists.getMessage());
    }
}
