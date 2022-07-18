package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.DataFile;

public interface DataFileMapper {


    /**
     * 插入数据
     * @param dataFile
     * @return
     */
    int insert(DataFile dataFile);


    /**
     * 根据fileId查询出指定的数据
     * @param fileId
     * @return
     */
    DataFile selectByFileId(String fileId);

}