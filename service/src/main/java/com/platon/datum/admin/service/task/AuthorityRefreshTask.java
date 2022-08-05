package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.service.VoteContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    private AuthorityMapper authorityMapper;
    @Resource
    private VoteContract voteContract;

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(fixedDelayString = "${AuthorityRefreshTask.fixedDelay}")
    public void task() {
        if(OrgCache.identityIdNotFound()){
            return;
        }
        List<Authority> allAuthority = voteContract.getAllAuthority();
        //将原来的删除并将新的存进去
        authorityMapper.refresh(allAuthority);
    }

}
