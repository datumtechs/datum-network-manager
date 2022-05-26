package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.dao.LocalOrgMapper;
import com.platon.datum.admin.dao.cache.LocalOrgCache;
import com.platon.datum.admin.dao.entity.LocalOrg;
import com.platon.datum.admin.service.LocalOrgService;
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
