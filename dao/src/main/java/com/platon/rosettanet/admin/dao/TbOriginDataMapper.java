package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbOriginData;

public interface TbOriginDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbOriginData record);

    int insertSelective(TbOriginData record);

    TbOriginData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbOriginData record);

    int updateByPrimaryKey(TbOriginData record);
}