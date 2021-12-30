package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.cache.LocalOrgCache;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.grpc.client.YarnClient;
import com.platon.metis.admin.service.LocalOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/10 10:36
 * @Version
 * @Desc
 */

@Slf4j
@Service
public class LocalOrgServiceImpl implements LocalOrgService {


    @Resource
    private LocalOrgMapper localOrgMapper;

    @Resource
    private YarnClient yarnClient;

    @Override
    public String getIdentityId() {
        return localOrgMapper.selectIdentityId();
    }

    @Override
    public LocalOrg getLocalOrg() {
        LocalOrg localOrg =  localOrgMapper.select();
        LocalOrgCache.setLocalOrgInfo(localOrg);
        return localOrg;
    }

    @Override
    public void updateLocalOrg(LocalOrg localOrg) {
        localOrgMapper.update(localOrg);
    }
}
