package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbDataNode;

public interface TbDataNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbDataNode record);

    int insertSelective(TbDataNode record);

    TbDataNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbDataNode record);

    int updateByPrimaryKey(TbDataNode record);
}