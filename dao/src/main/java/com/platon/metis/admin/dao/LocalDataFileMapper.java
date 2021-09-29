package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalDataFile;

public interface LocalDataFileMapper {


    /**
     * 插入数据
     * @param localDataFile
     * @return
     */
    int insert(LocalDataFile localDataFile);


    /**
     * 根据fileId查询出指定的数据
     * @param fileId
     * @return
     */
    LocalDataFile selectByFileId(String fileId);

}