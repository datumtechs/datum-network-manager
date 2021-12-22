package com.platon.metis.admin.common.exception;

public class FileNameError extends BizException {
    public FileNameError() {
        super(Errors.FileNameError.getCode(), Errors.FileNameError.getMessage());
    }
}
