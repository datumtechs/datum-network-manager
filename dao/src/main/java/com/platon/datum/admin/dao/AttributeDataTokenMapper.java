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

    int insertAndReturnId(AttributeDataToken dataToken);

    AttributeDataToken selectDataTokenById(Integer dataTokenId);

    void updateStatusByCurrentUser(@Param("dataTokenId") Integer dataTokenId,
                                   @Param("status") int status,
                                   @Param("currentUserAddress") String currentUserAddress);

    List<AttributeDataToken> selectListByKeywordAndUser(@Param("keyword") String keyword, @Param("address") String address);

    List<AttributeDataToken> selectListByStatus(int status);

    void updateStatus(@Param("id") Integer id, @Param("status") int status);

    void updateTokenAddress(@Param("id") Integer id, @Param("tokenAddress") String tokenAddress);

    AttributeDataToken selectByDataTokenAddress(@Param("tokenAddress") String tokenAddress);

    int updateTotalByAddress(@Param("total")String total, @Param("address")String address);


}