package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalMetaData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalMetaDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocalMetaData record);

    int insertSelective(LocalMetaData record);


    List<LocalMetaData> listMetaData(@Param("keyword") String keyword);

    LocalMetaData selectByPrimaryKey(Integer id);

    LocalMetaData findWithTaskCount(Integer id);

    LocalMetaData selectByMetaDataId(String metaDataId);

    LocalMetaData checkResourceName(@Param("resourceName") String resourceName, @Param("fileId") String fileId);

    int updateByPrimaryKey(LocalMetaData record);

    void updateStatusByFileId(@Param("fileId") String fileId, @Param("status")Integer status);

    int updateStatusById(@Param("id") Integer id, @Param("status")Integer status);
}