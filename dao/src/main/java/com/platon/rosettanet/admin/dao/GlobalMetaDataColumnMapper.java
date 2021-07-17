package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.GlobalMetaDataColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GlobalMetaDataColumnMapper {


    /**
     * 根据metaDataId查询数据
     * @param metaDataId
     * @return
     */
    List<GlobalMetaDataColumn> selectByMetaDataId(String metaDataId);


    /**
     * 批量删除数据，一次建议最多删除1000条
     */
    int batchDeleteByMetaDataId(@Param("metaDataIdList") List<String> metaDataIdList);

    /**
     * 批量更新数据，一次建议最多更新1000条
     */
    int batchUpdateByMetaDataIdSelective(@Param("columnList") List<GlobalMetaDataColumn> columnList);

    /**
     * 批量新增数据，一次建议最多更新1000条
     */
    int batchAddSelective(@Param("columnList") List<GlobalMetaDataColumn> columnList);
}