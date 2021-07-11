package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
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
}