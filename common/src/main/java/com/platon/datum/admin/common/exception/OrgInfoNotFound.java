package com.platon.datum.admin.common.exception;

/**
 * @author liushuyu
 * @date 2022/8/11 11:18
 * @desc
 */
public class OrgInfoNotFound extends BizException {
    public OrgInfoNotFound() {
        super(Errors.OrgInfoNotFound);
    }

    /**
     * @param msg 打印在日志中的异常信息和返回给前端的响应信息
     */
    public OrgInfoNotFound(String msg) {
        super(Errors.OrgInfoNotFound, msg);
    }

    /**
     * @param t 打印在日志中的异常信息
     */
    public OrgInfoNotFound(Throwable t) {
        super(Errors.OrgInfoNotFound, t);
    }
}
