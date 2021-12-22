package com.platon.metis.admin.common.exception;

public class CannotEditPublishedFile extends BizException {
    public CannotEditPublishedFile() {
        super(Errors.CannotEditPublishedData.getCode(), Errors.CannotEditPublishedData.getMessage());
    }
}
