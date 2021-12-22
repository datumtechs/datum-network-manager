package com.platon.metis.admin;

import com.platon.metis.admin.dao.cache.LocalOrgCache;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.service.LocalOrgService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.platon.metis.admin.dao")
@Slf4j
public class MetisAdminApplication {

    @Resource
    private LocalOrgService localOrgService;

    @RequestMapping("/")
    public String home() {
        return "Hello Metis";
    }

    public static void main(String[] args) {
        SpringApplication.run(MetisAdminApplication.class, args);
    }


    //@Bean
    /*public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }*/

    /**
     * 应用启动后做一些操作
     * @throws Exception
     */
    @PostConstruct
    public void init() {
        log.info("应用已启动，执行初始化操作.............");

        LocalOrg localOrg = localOrgService.getLocalOrg();
        LocalOrgCache.setLocalOrgInfo(localOrg);

        log.info("执行初始化操作执行完成............");
    }
}
