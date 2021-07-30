package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GlobalDataFileMapper {

    /**
     * 根据id查询出指定的数据
     * @param id
     * @return
     */
    GlobalDataFile selectById(Integer id);

    /**
     * 获取所有文件信息
     * @param keyword
     */
    List<GlobalDataFile> listDataFile(String keyword);

    /**
     * 批量更新数据，一次建议最多更新1000条
     */
    int batchUpdateByFileIdSelective(@Param("localDataFileList") List<GlobalDataFile> localDataFileList);

    /**
     * 批量删除数据，一次建议最多删除1000条
     */
    int batchDeleteByFileId(@Param("fileIdList") List<String> fileIdList);

    /**
     * 查询数据库中的所有fileId
     * @return
     */
    List<String> selectAllFileId();

    /**
     * 批量新增数据，一次建议最多更新1000条
     * @param localDataFileList
     * @return
     */
    int batchAddSelective(@Param("localDataFileList") List<GlobalDataFile> localDataFileList);
}