package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalDataHost;

public interface LocalDataHostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocalDataHost record);

    int insertSelective(LocalDataHost record);

    LocalDataHost selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LocalDataHost record);

    int updateByPrimaryKey(LocalDataHost record);
}