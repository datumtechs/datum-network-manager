package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalOrg;

public interface LocalOrgMapper {

    int insertSelective(LocalOrg record);

    LocalOrg selectAvailableCarrier();

    String selectIdentityId();
}