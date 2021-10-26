package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.GlobalMetaDataColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GlobalMetaDataColumnMapper {


    /**
     * 根据metaDataId查询数据
     * @param metaDataId
     * @return
     */
    List<GlobalMetaDataColumn> selectByMetaDataId(@Param("metaDataId") String metaDataId);


    /**
     * 批量删除数据，一次建议最多删除1000条
     */
    int deleteByMetaDataId(@Param("metaDataId") String metaDataId);

    /**
     * 批量新增数据，一次建议最多更新1000条
     */
    int batchInsert(@Param("globalMetaDataColumnList") List<GlobalMetaDataColumn> globalMetaDataColumnList);
}