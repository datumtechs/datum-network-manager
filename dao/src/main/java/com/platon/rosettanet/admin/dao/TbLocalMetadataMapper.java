package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbLocalMetadata;

public interface TbLocalMetadataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbLocalMetadata record);

    int insertSelective(TbLocalMetadata record);

    TbLocalMetadata selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbLocalMetadata record);

    int updateByPrimaryKey(TbLocalMetadata record);
}