package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalDataAuth;
import com.platon.rosettanet.admin.dao.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface LocalDataAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LocalDataAuth record);

    int insertSelective(LocalDataAuth record);

    int insertBatch(List<LocalDataAuth> dataAuthList);

    LocalDataAuth selectByPrimaryKey(Integer id);

    List<LocalDataAuth> selectDataAuthList(@Param("status") int status, @Param("keyword") String keyword);

    List<String> selectDataAuthByStatusWithAuthFinish();

    int selectFinishAuthCount();

    int selectUnfinishAuthCount();

    int updateByPrimaryKeySelective(LocalDataAuth record);

    int updateByPrimaryKey(LocalDataAuth record);

}