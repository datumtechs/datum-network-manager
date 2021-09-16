package com.platon.metis.admin.interceptor;

import cn.hutool.json.JSONUtil;
import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.enums.ResponseCodeEnum;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author liushuyu
 * @Date 2021/7/17 17:41
 * @Version
 * @Desc
 */
public class ApplyOrgIdentityInterceptor implements HandlerInterceptor {

    /**
     * 目标方法执行之前
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler) throws Exception {
        Object localOrgInfo = LocalOrgCache.getLocalOrgInfo();
        if(localOrgInfo != null){//已申请身份标识
            return true;
        } else {//未申请身份标识，则返回错误信息
            servletResponse.setContentType("application/json; charset=utf-8");
            JsonResponse no_apply = JsonResponse.fail(ResponseCodeEnum.IDENTITY_ID_MISSING);
            servletResponse.getWriter().write(JSONUtil.toJsonStr(no_apply));
            servletResponse.setStatus(200);
            return false;
        }
    }

}
