package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.GlobalMetaDataColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GlobalMetaDataColumnMapper {


    /**
     * 根据fileId查询数据
     * @param fileId
     * @return
     */
    List<GlobalMetaDataColumn> selectByFileId(String fileId);


    /**
     * 批量删除数据，一次建议最多删除1000条
     */
    int batchDeleteByFileId(@Param("fileIdList") List<String> fileIdList);

    /**
     * 批量更新数据，一次建议最多更新1000条
     */
    int batchUpdateByFileIdSelective(@Param("columnList") List<GlobalMetaDataColumn> columnList);

    /**
     * 批量新增数据，一次建议最多更新1000条
     */
    int batchAddSelective(@Param("columnList") List<GlobalMetaDataColumn> columnList);
}