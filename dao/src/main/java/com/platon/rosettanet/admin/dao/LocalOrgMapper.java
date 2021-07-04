package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalOrg;

public interface LocalOrgMapper {
    int insert(LocalOrg record);

    int insertSelective(LocalOrg record);
}