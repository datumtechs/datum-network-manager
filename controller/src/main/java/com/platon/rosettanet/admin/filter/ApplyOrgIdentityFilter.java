package com.platon.rosettanet.admin.filter;

import cn.hutool.json.JSONUtil;
import com.platon.rosettanet.admin.common.context.LocalOrgCache;
import com.platon.rosettanet.admin.dto.JsonResponse;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author liushuyu
 * @Date 2021/7/14 15:05
 * @Version
 * @Desc
 */

/**
 * 身份标识申请过滤器，如果未申请身份标识，则无法调用本系统服务
 */

//@Component
public class ApplyOrgIdentityFilter  implements Filter {

    private static String NO_APPLY_ORGIDENTITY = "您还没有申请身份标识";

    private static String[] excludeUrls = new String[]{"/api/v1/system/user/login",
            "/api/v1/system/user/logout",
            "/api/v1/system/user/verificationCode",
            "/api/v1/system/user/applyOrgIdentity"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse)servletResponse;
        String url=httpServletRequest.getServletPath();
        boolean needFilter = isNeedFilter(url);

        if(!needFilter){  //需要放行的请求
            filterChain.doFilter(servletRequest,servletResponse);
        }else{ //进行拦截返回错误信息
            Object localOrgInfo = LocalOrgCache.getLocalOrgInfo();
            if(localOrgInfo != null){//已申请身份标识
                filterChain.doFilter(servletRequest,servletResponse);
            } else {//未申请身份标识，则返回错误信息
                httpServletResponse.setContentType("application/json; charset=utf-8");
                JsonResponse no_apply = JsonResponse.fail(NO_APPLY_ORGIDENTITY);
                httpServletResponse.getWriter().write(JSONUtil.toJsonStr(no_apply));
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
