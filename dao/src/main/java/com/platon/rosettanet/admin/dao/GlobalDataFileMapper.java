package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
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
     * 获取所有文件信息
     * @param keyword
     */
    List<GlobalDataFile> listDataFile(String keyword);


    /**
     *根据metaDataId进行选择性更新
     */
    int updateByMetaDataIdSelective(GlobalDataFile localDataFile);

    /**
     * 批量更新数据，一次建议最多更新1000条
     */
    int batchUpdateByMetaDataIdSelective(@Param("localDataFileList") List<GlobalDataFile> localDataFileList);

    /**
     * 批量删除数据，一次建议最多删除1000条
     */
    int batchDeleteByMetaDataId(@Param("metaDataIdList") List<String> metaDataIdList);

    /**
     * 查询数据库中的所有metaDataId
     * @return
     */
    List<String> selectAllMetaDataId();

    /**
     * 批量新增数据，一次建议最多更新1000条
     * @param localDataFileList
     * @return
     */
    int batchAddSelective(@Param("localDataFileList") List<GlobalDataFile> localDataFileList);
}