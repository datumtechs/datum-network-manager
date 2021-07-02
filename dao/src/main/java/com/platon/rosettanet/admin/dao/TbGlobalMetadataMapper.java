package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbGlobalMetadata;

public interface TbGlobalMetadataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbGlobalMetadata record);

    int insertSelective(TbGlobalMetadata record);

    TbGlobalMetadata selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbGlobalMetadata record);

    int updateByPrimaryKey(TbGlobalMetadata record);
}