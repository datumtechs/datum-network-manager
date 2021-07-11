package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalOrg;

public interface LocalOrgMapper {

    int insertSelective(LocalOrg record);

    /**
     * 获取可用的调度服务信息
     * @return
     */
    LocalOrg selectAvailableCarrier();

    /**
     * 获取组织信息
     * @return
     */
    LocalOrg select();

    String selectIdentityId();
}