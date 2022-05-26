package com.platon.datum.admin.config;

import com.platon.datum.admin.interceptor.ApplyOrgIdentityInterceptor;
import com.platon.datum.admin.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author liushuyu
 * @Date 2021/7/1 18:47
 * @Version
 * @Desc
 */


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludeUrls = new String[]{
                "/swagger-resources/**",
                "/v2/api-docs",
                "/**/*.js",
                "/**/*.css",
                "/**/*.html",
                "/**/*.map",
                "/health",
                "/api/v1/user/login",
                "/api/v1/user/logout",
                "/api/v1/user/getLoginNonce",
                "/api/v1/system/getMetaMaskConfig",
                "/api/v1/system/getSystemConfigKey",
                "/api/v1/system/getAllConfig",
                "/api/v1/system/getConfigByKey"
        };
        registry.addInterceptor(new LoginInterceptor())//使用那个拦截器
                .addPathPatterns("/**")//所有请求都被拦截包括静态资源了。
                .excludePathPatterns(excludeUrls)//设置要放行的页面。
                .order(-1);
        registry.addInterceptor(new ApplyOrgIdentityInterceptor())//使用那个拦截器
                .addPathPatterns("/**")//所有请求都被拦截包括静态资源了。
                .excludePathPatterns(excludeUrls)//设置要放行的页面。
                .excludePathPatterns("/api/v1/user/applyOrgIdentity")
                .order(1);
    }
}
