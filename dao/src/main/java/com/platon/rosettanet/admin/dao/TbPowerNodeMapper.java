package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbPowerNode;

public interface TbPowerNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbPowerNode record);

    int insertSelective(TbPowerNode record);

    TbPowerNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbPowerNode record);

    int updateByPrimaryKey(TbPowerNode record);
}