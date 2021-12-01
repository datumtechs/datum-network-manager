package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalOrg;

public interface LocalOrgMapper {

    /**
     * 选择性插入字段
     * @param record
     * @return
     */
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

    /**
     * 查询组织身份标识
     * @return
     */
    String selectIdentityId();

    /**
     * 更新
     * @param localOrg
     * @return
     */
    int update(LocalOrg localOrg);
}