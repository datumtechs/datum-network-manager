package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalDataAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalDataAuthMapper {
    int deleteByPrimaryKey(String authId);

    int insert(LocalDataAuth record);

    int insertSelective(LocalDataAuth record);

    int insertBatch(List<LocalDataAuth> dataAuthList);

    LocalDataAuth selectByPrimaryKey(String authId);

    List<LocalDataAuth> selectDataAuthList(@Param("status") int status, @Param("keyword") String keyword);

    List<LocalDataAuth> listAll();

    int selectFinishAuthCount();

    int selectUnfinishAuthCount();

    int updateByPrimaryKeySelective(LocalDataAuth record);

    int updateByPrimaryKey(LocalDataAuth record);

}