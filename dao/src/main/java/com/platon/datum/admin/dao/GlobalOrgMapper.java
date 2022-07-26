package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.GlobalOrg;

/**
 * @Author juzix
 * @Date 2022/7/21 14:07
 * @Version
 * @Desc ******************************
 */
public interface GlobalOrgMapper {
    int deleteByIdentityId(String identityId);

    int insert(GlobalOrg record);

    GlobalOrg selectByIdentityId(String identityId);

    int updateByIdentityIdSelective(GlobalOrg record);

    int updateByIdentityId(GlobalOrg record);
}