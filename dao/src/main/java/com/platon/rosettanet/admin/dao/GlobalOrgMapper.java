package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.GlobalOrg;

public interface GlobalOrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GlobalOrg record);

    int insertSelective(GlobalOrg record);

    GlobalOrg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GlobalOrg record);

    int updateByPrimaryKey(GlobalOrg record);
}