package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbLocalMetadataColumn;

public interface TbLocalMetadataColumnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbLocalMetadataColumn record);

    int insertSelective(TbLocalMetadataColumn record);

    TbLocalMetadataColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbLocalMetadataColumn record);

    int updateByPrimaryKey(TbLocalMetadataColumn record);
}