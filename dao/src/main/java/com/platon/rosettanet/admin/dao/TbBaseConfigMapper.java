package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbBaseConfig;

public interface TbBaseConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbBaseConfig record);

    int insertSelective(TbBaseConfig record);

    TbBaseConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbBaseConfig record);

    int updateByPrimaryKey(TbBaseConfig record);
}