package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbTaskPowerProvider;

public interface TbTaskPowerProviderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbTaskPowerProvider record);

    int insertSelective(TbTaskPowerProvider record);

    TbTaskPowerProvider selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbTaskPowerProvider record);

    int updateByPrimaryKey(TbTaskPowerProvider record);
}