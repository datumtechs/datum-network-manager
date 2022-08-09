package com.platon.datum.admin.service;

import com.platon.datum.admin.dao.entity.Org;

/**
 * @Author liushuyu
 * @Date 2021/7/10 10:36
 * @Version
 * @Desc
 */
public interface OrgService {

    /**
     * 获取本组织ID
     * @return
     */
    String getIdentityId();

    /**
     * 获取本组织信息
     * @return
     */
    Org getLocalOrg();

    void updateLocalOrg(Org org);

    void updateCredential(String credential);
}
