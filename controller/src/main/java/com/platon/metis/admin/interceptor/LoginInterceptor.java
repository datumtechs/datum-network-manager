package com.platon.metis.admin.interceptor;

import cn.hutool.json.JSONUtil;
import com.platon.metis.admin.constant.ControllerConstants;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.enums.ResponseCodeEnum;
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
        HttpSession session = servletRequest.getSession();
        if(session != null && session.getAttribute(ControllerConstants.USER_ID) != null){//已登录
            return true;
        } else {//未登录，则返回错误信息
            servletResponse.setContentType("application/json; charset=utf-8");
            JsonResponse noLogin = JsonResponse.fail(ResponseCodeEnum.NO_LOGIN);
            servletResponse.getWriter().write(JSONUtil.toJsonStr(noLogin));
            servletResponse.setStatus(200);
            return false;
        }
    }

}
