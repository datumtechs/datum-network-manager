package com.platon.metis.admin.common.exception;

public class CannotDeletePublishedFile extends BizException {
    public CannotDeletePublishedFile() {
        super(Errors.CannotDeletePublishedData);
    }
}
