package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.AttributeDataToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/7/13 14:54
 * @Version
 * @Desc ******************************
 */
public interface AttributeDataTokenMapper {


    AttributeDataToken selectByMetaDataId(Integer metaDataId);

    AttributeDataToken selectPublishedByMetaDataId(Integer metaDataId);

    int insertAndReturnId(AttributeDataToken dataToken);

    AttributeDataToken selectDataTokenById(Integer dataTokenId);

    void updateStatusById(@Param("dataTokenId") Integer dataTokenId,
                          @Param("status") int status);

    List<AttributeDataToken> selectListByKeywordAndUser(@Param("keyword") String keyword, @Param("address") String address);

    List<AttributeDataToken> selectListByStatus(@Param("statusList") List<Integer> statusList);

    void updateStatus(@Param("id") Integer id, @Param("status") int status);

    void updateTokenAddress(@Param("id") Integer id, @Param("tokenAddress") String tokenAddress);

    AttributeDataToken selectByDataTokenAddress(@Param("tokenAddress") String tokenAddress);

    int updateTotalByAddress(@Param("total") String total, @Param("address") String address);

    AttributeDataToken selectByAddress(@Param("attributeDataTokenAddress") String attributeDataTokenAddress);

    int count();
}