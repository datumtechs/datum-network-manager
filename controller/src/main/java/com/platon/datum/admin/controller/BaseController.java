package com.platon.datum.admin.controller;

import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.constant.ControllerConstants;
import com.platon.datum.admin.dao.entity.SysUser;

import javax.servlet.http.HttpSession;

/**
 * @Author liushuyu
 * @Date 2022/3/30 10:43
 * @Version
 * @Desc
 */
public abstract class BaseController {

    /**
     * 获取当前用户钱包地址
     *
     * @param session
     * @return
     */
    protected String getCurrentUserAddress(HttpSession session) {
        SysUser userInfo = (SysUser) session.getAttribute(ControllerConstants.USER_INFO);
        if (userInfo == null) {
            throw new BizException(Errors.UserNotLogin);
        }
        //获取当前用户钱包地址
        String address = userInfo.getAddress();
        return address;
    }

    /**
     * 判断当前用户是否是管理员
     *
     * @param session
     * @return
     */
    protected boolean currentUserIsAdmin(HttpSession session) {
        SysUser userInfo = (SysUser) session.getAttribute(ControllerConstants.USER_INFO);
        if (userInfo == null) {
            throw new BizException(Errors.UserNotLogin);
        }
        return userInfo.getIsAdmin() == 1;
    }
}
