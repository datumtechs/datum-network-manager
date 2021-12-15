package com.platon.metis.admin.interceptor;

import com.platon.metis.admin.common.exception.UserNotLogin;
import com.platon.metis.admin.constant.ControllerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        if(session != null && session.getAttribute(ControllerConstants.USER_ID) != null){//已登录
            return true;
        } else {//未登录，则返回错误信息
            throw new UserNotLogin();
        }
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
