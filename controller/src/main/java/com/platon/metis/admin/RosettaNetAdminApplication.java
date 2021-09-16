package com.platon.metis.admin;

import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.service.LocalOrgService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.platon.rosettanet.admin.dao"})
@Slf4j
public class RosettaNetAdminApplication {

    @Resource
    private LocalOrgService localOrgService;

    public static void main(String[] args) {
        SpringApplication.run(RosettaNetAdminApplication.class, args);
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

        /**
         * 设置组织ID，供全局使用，如果未插入，则返回空
         */
        LocalOrgIdentityCache.setIdentityId(localOrg == null ? null :localOrg.getIdentityId());
        log.info("执行初始化操作执行完成.............");
    }
}
