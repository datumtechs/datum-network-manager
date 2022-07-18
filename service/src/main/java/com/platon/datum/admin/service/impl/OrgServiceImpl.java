package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.dao.OrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.service.OrgService;
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
public class OrgServiceImpl implements OrgService {


    @Resource
    private OrgMapper orgMapper;

    @Override
    public String getIdentityId() {
        return orgMapper.selectIdentityId();
    }

    @Override
    public Org getLocalOrg() {
        Org org =  orgMapper.select();
        OrgCache.setLocalOrgInfo(org);
        return org;
    }

    @Override
    public void updateLocalOrg(Org org) {
        orgMapper.update(org);
    }
}
