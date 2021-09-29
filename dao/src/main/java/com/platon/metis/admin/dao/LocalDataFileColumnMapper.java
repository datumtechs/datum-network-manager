package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalDataFileColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalDataFileColumnMapper {
    /**
     * 批量新增数据，一次建议最多更新1000条
     */
    int insertBatch(@Param("columnList") List<LocalDataFileColumn> columnList);

    List<LocalDataFileColumn> listByFileId(@Param("fileId") String fileId);
}
