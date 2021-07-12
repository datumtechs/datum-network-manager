package com.platon.rosettanet.admin;

import com.platon.rosettanet.admin.common.context.LocalOrgCache;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import com.platon.rosettanet.admin.service.LocalOrgService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.platon.rosettanet.admin.dao"})
@Slf4j
public class RosettaNetAdminApplication implements ApplicationRunner {

    @Resource
    private LocalOrgService localOrgService;

    public static void main(String[] args) {
        SpringApplication.run(RosettaNetAdminApplication.class, args);
    }


    /**
     * 应用启动后做一些操作
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args){
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
