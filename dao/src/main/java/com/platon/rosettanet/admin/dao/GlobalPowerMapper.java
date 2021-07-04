package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.GlobalPower;

public interface GlobalPowerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GlobalPower record);

    int insertSelective(GlobalPower record);

    GlobalPower selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GlobalPower record);

    int updateByPrimaryKey(GlobalPower record);
}