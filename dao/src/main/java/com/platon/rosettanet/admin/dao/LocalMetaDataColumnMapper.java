package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;

public interface LocalMetaDataColumnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocalMetaDataColumn record);

    int insertSelective(LocalMetaDataColumn record);

    LocalMetaDataColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LocalMetaDataColumn record);

    int updateByPrimaryKey(LocalMetaDataColumn record);
}