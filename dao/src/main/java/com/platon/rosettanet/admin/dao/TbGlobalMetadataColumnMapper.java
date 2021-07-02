package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbGlobalMetadataColumn;

public interface TbGlobalMetadataColumnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbGlobalMetadataColumn record);

    int insertSelective(TbGlobalMetadataColumn record);

    TbGlobalMetadataColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbGlobalMetadataColumn record);

    int updateByPrimaryKey(TbGlobalMetadataColumn record);
}