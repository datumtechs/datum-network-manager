package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.DataAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataAuthMapper {
    int deleteByPrimaryKey(String authId);

    int insert(DataAuth record);

    int insertBatch(List<DataAuth> dataAuthList);
    int replaceBatch(List<DataAuth> dataAuthList);

    DataAuth selectByPrimaryKey(String authId);

    List<DataAuth> selectDataAuthList(@Param("status") int status, @Param("keyword") String keyword);

    List<DataAuth> listAll();

    int selectFinishAuthCount();

    int selectUnfinishAuthCount();

    int updateByPrimaryKeySelective(DataAuth record);

    int updateByPrimaryKey(DataAuth record);

    /**
     * 将过期的授权申请设置为拒绝
     * @return
     */
    int updateExpireAuthData();

    /**
     * 查询出已撤销元数据对应的待审核的数据授权ID
     */
    List<String> selectAuthIdListWithRevokedMetaData();
}