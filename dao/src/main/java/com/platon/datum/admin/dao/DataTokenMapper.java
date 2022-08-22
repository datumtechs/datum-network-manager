package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.DataToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataTokenMapper {

    /**
     */
    List<DataToken> selectListByAddress(@Param("keyword") String keyword, @Param("address") String address);

    /**
     * 插入凭证信息
     *
     * @param dataToken
     * @return 返回凭证id
     */
    int insertAndReturnId(DataToken dataToken);

    /**
     * 更新定价状态
     *
     * @param dataToken
     */
    int updatePricingStatus(DataToken dataToken);

    int updateStatus(@Param("id") int id, @Param("status") int status);

    int updateStatusByCurrentUser(@Param("id") int id, @Param("status") int status);

    DataToken selectById(Integer id);

    List<DataToken> selectListByStatus(int status);

    int updateTokenAddress(@Param("id") int id, @Param("tokenAddress") String tokenAddress);

    DataToken selectByMetaDataId(int metaDataDbId);

    DataToken selectByAddress(@Param("address") String address);

    DataToken selectPublishedByMetaDataId(int metaDataDbId);

    void updateFeeById(@Param("dataTokenId") Integer dataTokenId, @Param("ciphertextFee") String ciphertextFee, @Param("plaintextFee") String plaintextFee);

    void updateNewFeeById(@Param("dataTokenId") Integer dataTokenId, @Param("ciphertextFee") String ciphertextFee, @Param("plaintextFee") String plaintextFee);

    int count();
}
