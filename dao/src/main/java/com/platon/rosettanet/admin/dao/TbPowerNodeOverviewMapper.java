package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbPowerNodeOverview;

public interface TbPowerNodeOverviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbPowerNodeOverview record);

    int insertSelective(TbPowerNodeOverview record);

    TbPowerNodeOverview selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbPowerNodeOverview record);

    int updateByPrimaryKey(TbPowerNodeOverview record);
}