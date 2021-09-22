package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalMetaData;
import com.platon.metis.admin.dao.entity.LocalMetaDataItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalMetaDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocalMetaData record);

    int insertSelective(LocalMetaData record);


    List<LocalMetaDataItem> listMetaData(@Param("keyword") String keyword);

    LocalMetaData selectByPrimaryKey(Integer id);

    LocalMetaData selectByDataFileId(Integer dataFileId);

    LocalMetaData selectByMetaDataId(String metaDataId);

    LocalMetaData selectByResourceName(@Param("resourceName") String resourceName, @Param("id") Integer id);


    int updateByPrimaryKeySelective(LocalMetaData record);

    int updateByPrimaryKey(LocalMetaData record);


}