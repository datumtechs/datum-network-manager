package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalDataFile;

import java.util.List;

public interface LocalDataFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocalDataFile record);

    int insertSelective(LocalDataFile record);

    LocalDataFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LocalDataFile record);

    int updateByPrimaryKey(LocalDataFile record);

    List<LocalDataFile> listDataFile(int offset, int pageSize);

    int listDataFileCount();
}