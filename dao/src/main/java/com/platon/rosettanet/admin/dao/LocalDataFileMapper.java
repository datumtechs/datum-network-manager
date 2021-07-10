package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface LocalDataFileMapper {

    List<LocalDataFile> listDataFile();

    /**
     * 插入不为空的字段
     * @param localDataFile
     * @return
     */
    int insertSelective(LocalDataFile localDataFile);
}