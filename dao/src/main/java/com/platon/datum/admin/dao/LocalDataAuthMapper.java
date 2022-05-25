package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.LocalDataAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalDataAuthMapper {
    int deleteByPrimaryKey(String authId);

    int insert(LocalDataAuth record);

    int insertBatch(List<LocalDataAuth> dataAuthList);
    int replaceBatch(List<LocalDataAuth> dataAuthList);

    LocalDataAuth selectByPrimaryKey(String authId);

    List<LocalDataAuth> selectDataAuthList(@Param("status") int status, @Param("keyword") String keyword);

    List<LocalDataAuth> listAll();

    int selectFinishAuthCount();

    int selectUnfinishAuthCount();

    int updateByPrimaryKeySelective(LocalDataAuth record);

    int updateByPrimaryKey(LocalDataAuth record);

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