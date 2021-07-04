package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.GlobalMetaDataColumn;

public interface GlobalMetaDataColumnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GlobalMetaDataColumn record);

    int insertSelective(GlobalMetaDataColumn record);

    GlobalMetaDataColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GlobalMetaDataColumn record);

    int updateByPrimaryKey(GlobalMetaDataColumn record);
}