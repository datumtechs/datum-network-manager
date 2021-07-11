package com.platon.rosettanet.admin.dao;

public interface GlobalDataHostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GlobalDataHost record);

    int insertSelective(GlobalDataHost record);

    GlobalDataHost selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GlobalDataHost record);

    int updateByPrimaryKey(GlobalDataHost record);
}