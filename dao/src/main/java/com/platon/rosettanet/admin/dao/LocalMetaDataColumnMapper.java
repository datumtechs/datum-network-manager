package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalMetaDataColumnMapper {

    /**
     * 根据文件id查询数据
     * @param fileId 关联的文件id
     * @return
     */
    List<LocalMetaDataColumn> selectByFileId(String fileId);

    /**
     * 批量插入
     * @param columnList
     * @return
     */
    int batchInsert(@Param("columnList") List<LocalMetaDataColumn> columnList);

    /**
     * 根据fileId删除指定的数据,released的数据不可删除
     * @param fileId
     * @return
     */
    int deleteByFileId(String fileId);

    /**
     * 根据Id进行选择性更新数据
     * @param
     * @return
     */
    int updateByIdSelective(LocalMetaDataColumn localMetaDataColumn);

    /**
     * 根据metaDataId 和 cindex修改数据
     * @param
     * @return
     */
    int updateByFileIdAndCindexSelective(LocalMetaDataColumn localMetaDataColumn);
}