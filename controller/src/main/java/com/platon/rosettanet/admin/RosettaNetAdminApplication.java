package com.platon.rosettanet.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.platon.rosettanet.admin.dao"})
public class RosettaNetAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(RosettaNetAdminApplication.class, args);
    }
}
