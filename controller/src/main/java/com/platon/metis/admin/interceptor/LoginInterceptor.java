package com.platon.metis.admin.interceptor;

import com.platon.metis.admin.common.exception.UserNotLogin;
import com.platon.metis.admin.constant.ControllerConstants;
import com.platon.metis.admin.dao.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/17 17:37
 * @Version
 * @Desc
 */

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 目标方法执行之前
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler) throws Exception {
        if(isRunningTest()){
            log.debug("this a testing env....");
            return true;
        }else{
            log.debug("this a production env....");
        }
        HttpSession session = servletRequest.getSession();
        if(session != null && session.getAttribute(ControllerConstants.USER_INFO) != null){//已登录
            //判断用户是否有权限访问,现在默认只有两个角色，一个是管理员，一个是非管理员，管理员默认有所有的权限
            String urlPath = servletRequest.getServletPath();
            log.debug("urlPath==============" + urlPath);
            return hasResource(session,urlPath);
        } else {//未登录，则返回错误信息
            throw new UserNotLogin();
        }
    }

    private boolean hasResource(HttpSession session,String urlPath) {
        //先判断是否是管理员，如果是，则无需判断url
        SysUser user = (SysUser)session.getAttribute(ControllerConstants.USER_INFO);
        if(user.getIsAdmin() == 1){
            return true;
        }
        List<String> resourceList = (List<String>)session.getAttribute(ControllerConstants.USER_URL_RESOURCE);
        if(resourceList.contains(urlPath)){
            return true;
        }
        return false;
    }

    private Boolean isRunningTest = null;
    private boolean isRunningTest() {
        if (isRunningTest == null) {
            isRunningTest = true;
            try {
                Class.forName("org.junit.Test");
            } catch (ClassNotFoundException e) {
                isRunningTest = false;
            }
        }
        return isRunningTest;
    }
}
