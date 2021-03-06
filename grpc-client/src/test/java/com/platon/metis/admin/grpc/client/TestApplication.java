package com.platon.metis.admin.grpc.client;

import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.entity.LocalOrg;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = "com.platon.metis.admin")
@MapperScan(basePackages = {"com.platon.metis.admin.dao"})
@Slf4j
public class TestApplication {

    @Resource
    private LocalOrgMapper localOrgMapper;

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

        LocalOrg localOrg = localOrgMapper.select();
        LocalOrgCache.setLocalOrgInfo(localOrg);

        /**
         * 设置组织ID，供全局使用，如果未插入，则返回空
         */
        LocalOrgIdentityCache.setIdentityId(localOrg == null ? null :localOrg.getIdentityId());
        log.info("执行初始化操作执行完成.............");
    }
}
