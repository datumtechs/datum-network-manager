package com.platon.datum.admin.service;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author liushuyu
 * @Date 2022/7/25 12:19
 * @Version
 * @Desc
 */

@EnableFeignClients
public class ServiceTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTestApplication.class, args);
    }
}
