package com.platon.rosettanet.admin.service;

import com.platon.rosettanet.admin.dao.entity.LocalOrg;

/**
 * @Author liushuyu
 * @Date 2021/7/10 10:36
 * @Version
 * @Desc
 */
public interface LocalOrgService {

    /**
     * 获取本组织ID
     * @return
     */
    String getIdentityId();

    /**
     * 获取本组织信息
     * @return
     */
    LocalOrg getLocalOrg();

}
