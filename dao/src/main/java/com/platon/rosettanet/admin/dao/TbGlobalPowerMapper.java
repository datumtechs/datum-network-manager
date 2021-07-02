package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbGlobalPower;

public interface TbGlobalPowerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbGlobalPower record);

    int insertSelective(TbGlobalPower record);

    TbGlobalPower selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbGlobalPower record);

    int updateByPrimaryKey(TbGlobalPower record);
}