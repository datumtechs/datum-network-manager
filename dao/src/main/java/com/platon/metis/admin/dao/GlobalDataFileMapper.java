package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.GlobalDataFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GlobalDataFileMapper {

    /**
     * 根据metaDataId查询出指定的数据
     * @param metaDataId
     * @return
     */
    GlobalDataFile selectByMetaDataId(String metaDataId);

    /**
     * 查询数据库中的所有MetaDataId
     * @return
     */
    List<String> selectAllMetaDataId();


    /**
     * 获取所有文件信息
     * @param keyword
     */
    List<GlobalDataFile> listDataFile(String keyword);

    /**
     * 批量保存数据，存在时更新，否则新增
     * @param globalDataFileList
     * @return
     */
    int batchInsert(@Param("globalDataFileList") List<GlobalDataFile> globalDataFileList);

    /**
     * 批量保存数据，存在时更新，否则新增
     * @param globalDataFileList
     * @return
     */
    int batchUpdate(@Param("globalDataFileList") List<GlobalDataFile> globalDataFileList);
}