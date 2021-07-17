package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalMetaDataColumnMapper {

    /**
     * 根据metaDataId查询数据
     * @param metaDataId
     * @return
     */
    List<LocalMetaDataColumn> selectByMetaDataId(String metaDataId);

    /**
     * 根据metadataId和列下表索引进行选择性更新数据
     * @param record
     * @return
     */
    int updateByMetaDataIdAndIndexSelective(LocalMetaDataColumn record);


    /**
     * 批量插入
     * @param columnList
     * @return
     */
    int batchInsert(@Param("columnList") List<LocalMetaDataColumn> columnList);

    /**
     * 根据metaDataId删除指定的数据,released的数据不可删除
     * @param metaDataId
     * @return
     */
    int deleteByMetaDataId(String metaDataId);

    /**
     * 根据Id进行选择性更新数据
     * @param
     * @return
     */
    int updateByIdSelective(LocalMetaDataColumn localMetaDataColumn);
}