package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.service.LocalOrgService;
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
    public void createLocalOrg() {
        //创建本组织信息


        //创建成功后，将组织信息放入缓存中
        LocalOrgIdentityCache.setIdentityId("");
    }
}
