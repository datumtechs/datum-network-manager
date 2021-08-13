package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalMetaData;

public interface LocalMetaDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocalMetaData record);

    int insertSelective(LocalMetaData record);

    LocalMetaData selectByPrimaryKey(Integer id);

    LocalMetaData selectByFileId(String fileId);

    int updateByPrimaryKeySelective(LocalMetaData record);

    int updateByPrimaryKey(LocalMetaData record);


}