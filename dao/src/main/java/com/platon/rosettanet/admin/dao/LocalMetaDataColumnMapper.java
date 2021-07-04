package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;

import java.util.List;

public interface LocalMetaDataColumnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocalMetaDataColumn record);

    int insertSelective(LocalMetaDataColumn record);

    LocalMetaDataColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LocalMetaDataColumn record);

    int updateByPrimaryKey(LocalMetaDataColumn record);

    void insertBatch(List<LocalMetaDataColumn> localMetaDataColumnList);
}