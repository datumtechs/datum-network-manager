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

    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }
}
