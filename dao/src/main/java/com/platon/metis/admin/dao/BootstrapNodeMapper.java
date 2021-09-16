package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.BootstrapNode;

public interface BootstrapNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BootstrapNode record);

    int insertSelective(BootstrapNode record);

    BootstrapNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BootstrapNode record);

    int updateByPrimaryKey(BootstrapNode record);
}