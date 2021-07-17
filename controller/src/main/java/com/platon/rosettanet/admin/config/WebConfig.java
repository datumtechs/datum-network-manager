package com.platon.rosettanet.admin.config;

import com.platon.rosettanet.admin.interceptor.ApplyOrgIdentityInterceptor;
import com.platon.rosettanet.admin.interceptor.LoginInterceptor;
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
                "/api/v1/system/user/login",
                "/api/v1/system/user/logout",
                "/api/v1/system/user/verificationCode"};
        registry.addInterceptor(new LoginInterceptor())//使用那个拦截器
                .addPathPatterns("/**")//所有请求都被拦截包括静态资源了。
                .excludePathPatterns(excludeUrls)//设置要放行的页面。
                .order(-1);
        registry.addInterceptor(new ApplyOrgIdentityInterceptor())//使用那个拦截器
                .addPathPatterns("/**")//所有请求都被拦截包括静态资源了。
                .excludePathPatterns(excludeUrls)//设置要放行的页面。
                .excludePathPatterns("/api/v1/system/user/applyOrgIdentity")
                .order(1);
    }
}
