package com.platon.datum.admin;

import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.service.OrgService;
import com.platon.datum.admin.service.ProposalLogService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.platon.datum.admin.dao")
@Slf4j
@RestController
@RequestMapping("/")
public class DatumAdminApplication {

    @Resource
    private OrgService orgService;

    @Resource
    private ProposalLogService proposalLogService;

    public static void main(String[] args) {
        SpringApplication.run(DatumAdminApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "Hello, Datum-Admin";
    }

    /**
     * 应用启动后做一些操作
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() {
        log.info("应用已启动，执行初始化操作.............");

        Org org = orgService.select();
        OrgCache.setLocalOrgInfo(org);

        proposalLogService.subscribe();
        log.info("执行初始化操作执行完成............");
    }
}
