package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbPowerStatistic;

public interface TbPowerStatisticMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbPowerStatistic record);

    int insertSelective(TbPowerStatistic record);

    TbPowerStatistic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbPowerStatistic record);

    int updateByPrimaryKey(TbPowerStatistic record);
}