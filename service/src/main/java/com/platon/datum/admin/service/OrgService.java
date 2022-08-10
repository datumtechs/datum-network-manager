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
    String selectIdentityId();

    /**
     * 获取本组织信息
     * @return
     */
    Org select();

    Org updateSelective(Org org);

    Org insertSelective(Org record);

    Org update(Org org);

    Org updateIsAuthority(Integer isAuthority);

    Org updateCredential(String credential);
}
