package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbTask;

public interface TbTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbTask record);

    int insertSelective(TbTask record);

    TbTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbTask record);

    int updateByPrimaryKey(TbTask record);
}