package com.platon.metis.admin.controller;

import com.platon.metis.admin.constant.ControllerConstants;
import com.platon.metis.admin.dao.entity.SysUser;

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
     * @param session
     * @return
     */
    protected String getCurrentUserAddress(HttpSession session) {
        SysUser userInfo = (SysUser) session.getAttribute(ControllerConstants.USER_INFO);
        //获取当前用户钱包地址
        String address = userInfo.getAddress();
        return address;
    }

    /**
     * 判断当前用户是否是管理员
     * @param session
     * @return
     */
    protected boolean currentUserIsAdmin(HttpSession session) {
        SysUser userInfo = (SysUser) session.getAttribute(ControllerConstants.USER_INFO);
        return userInfo.getIsAdmin() == 1;
    }
}
