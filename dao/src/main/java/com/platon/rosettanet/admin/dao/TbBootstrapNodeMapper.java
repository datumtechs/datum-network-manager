package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbBootstrapNode;

public interface TbBootstrapNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbBootstrapNode record);

    int insertSelective(TbBootstrapNode record);

    TbBootstrapNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbBootstrapNode record);

    int updateByPrimaryKey(TbBootstrapNode record);
}