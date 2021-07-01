package com.platon.rosettanet.admin.filter;

import cn.hutool.json.JSONUtil;
import com.platon.rosettanet.admin.constant.ControllerConstants;
import com.platon.rosettanet.admin.dto.JsonResponse;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录过滤器
 */

@Component("loginFilter")
public class LoginFilter implements Filter {

    private static String NO_LOGIN = "您还没有登录";

    private static String[] excludeUrls = new String[]{"/api/v1/system/user/login.json",
                                        "/api/v1/system/user/logout.json",
                                        "/api/v1/system/user/verificationCode.json"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse)servletResponse;
        String url=httpServletRequest.getServletPath();
        boolean needFilter = isNeedFilter(url);

        if(!needFilter){  //需要放行的请求
            filterChain.doFilter(servletRequest,servletResponse);
        }else{ //进行拦截返回错误信息
            HttpSession session = httpServletRequest.getSession();
            if(session != null && session.getAttribute(ControllerConstants.USER_ID) != null){//已登录
                filterChain.doFilter(servletRequest,servletResponse);
            } else {//未登录，则返回错误信息
                httpServletResponse.setContentType("application/json; charset=utf-8");
                JsonResponse noLogin = JsonResponse.fail(NO_LOGIN);
                httpServletResponse.getWriter().write(JSONUtil.toJsonStr(noLogin));
                httpServletResponse.setStatus(401);
                return;
            }
        }

    }

    public boolean isNeedFilter(String url){
        for(String excludeUrl:excludeUrls){
            if(excludeUrl.equals(url)){
                return false;
            }
        }
        return true;
    }
}
