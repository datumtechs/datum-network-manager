package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2022/7/25 15:39
 * @Version
 * @Desc 刷新委员会成员列表
 */


@Slf4j
//@Configuration
public class AuthorityRefreshTask {

    @Resource
    private AuthorityService authorityService;

    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(fixedDelayString = "${AuthorityRefreshTask.fixedDelay}")
    public void task() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        authorityService.refreshAuthority();
    }

}
