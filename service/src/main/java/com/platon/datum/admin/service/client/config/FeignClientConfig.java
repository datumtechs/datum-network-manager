package com.platon.datum.admin.service.client.config;

import feign.Contract;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author liushuyu
 * @Date 2021/1/8 14:51
 * @Version
 * @Desc
 */

@EnableFeignClients(basePackages= "com.platon.datum.admin.service.client")
@Configuration
public class FeignClientConfig extends FeignClientsConfiguration {


    /**
     * 如果要使用feign的注解，则使用下面的bean。否则默认使用spring的@RequestMapping等注解
     */
//    @Bean
//    public Contract feignContract() {
//        return new Contract.Default();
//    }

}
