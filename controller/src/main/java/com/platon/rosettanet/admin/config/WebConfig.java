package com.platon.rosettanet.admin.config;

import com.platon.rosettanet.admin.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author liushuyu
 * @Date 2021/7/1 18:47
 * @Version
 * @Desc
 */


@Configuration
public class WebConfig {


    @Bean
    public FilterRegistrationBean registFilter(LoginFilter loginFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loginFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
