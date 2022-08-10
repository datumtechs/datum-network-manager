package com.platon.datum.admin.common.exception;

/**
 * @author liushuyu
 */
public class FileEmpty extends BizException {
    public FileEmpty() {
        super(Errors.FileEmpty);
    }

    public FileEmpty(String message) {
        super(Errors.FileEmpty, message);
    }
}
