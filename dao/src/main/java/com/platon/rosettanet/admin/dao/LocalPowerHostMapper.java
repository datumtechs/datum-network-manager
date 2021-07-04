package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalPowerHost;

public interface LocalPowerHostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocalPowerHost record);

    int insertSelective(LocalPowerHost record);

    LocalPowerHost selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LocalPowerHost record);

    int updateByPrimaryKey(LocalPowerHost record);
}