package com.platon.datum.admin.grpc.client;


import com.platon.datum.admin.dao.OrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = "com.platon.datum.admin")
@MapperScan(basePackages = {"com.platon.datum.admin.dao"})
@Slf4j
public class TestApplication {

    @Resource
    private OrgMapper orgMapper;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }


    /**
     * 应用启动后做一些操作
     * @throws Exception
     */
    @PostConstruct
    public void init() {
        log.info("应用已启动，执行初始化操作.............");

        Org org = orgMapper.select();
        OrgCache.setLocalOrgInfo(org);

        log.info("执行初始化操作执行完成.............");
    }
}
