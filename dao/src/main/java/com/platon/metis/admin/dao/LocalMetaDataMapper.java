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

    LocalMetaData checkResourceName(@Param("resourceName") String resourceName);

    int updateByPrimaryKey(LocalMetaData record);

    /**
     * 根据metaDataId, 更新 status, publish_time, rec_update_time
     *
     * @param localMetaDataList
     */
    void updateStatusByMetaDataIdBatch(@Param("localMetaDataList") List<LocalMetaData> localMetaDataList);

    int updateStatusById(@Param("id") Integer id, @Param("status")Integer status);
}