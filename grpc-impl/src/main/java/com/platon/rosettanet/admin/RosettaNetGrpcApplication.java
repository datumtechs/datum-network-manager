package com.platon.rosettanet.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.platon.rosettanet.admin.dao")
public class RosettaNetGrpcApplication {
    public static void main(String[] args) {
        SpringApplication.run(RosettaNetGrpcApplication.class, args);
    }
}
