package com.platon.metis.admin;

import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.service.LocalOrgService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.platon.metis.admin.dao")
@Slf4j
public class MetisAdminApplication {

    @Resource
    private LocalOrgService localOrgService;

    public static void main(String[] args) {
        SpringApplication.run(MetisAdminApplication.class, args);
    }


    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }

    /**
     * 应用启动后做一些操作
     * @throws Exception
     */
    @PostConstruct
    public void init() {
        log.info("应用已启动，执行初始化操作.............");

        LocalOrg localOrg = localOrgService.getLocalOrg();
        LocalOrgCache.setLocalOrgInfo(localOrg);
    }
}
