package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.service.LocalOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/10 10:36
 * @Version
 * @Desc
 */


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
        LocalOrg localOrg = localOrgMapper.select();
        return localOrg;
    }
}
