package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.DataToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataTokenMapper {

    /**
     * 查询已定价和未定价的dataToken
     * 所有状态：0-未发布，1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功
     * 前端传0-未定价则返回：0-未发布，1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，
     * 前端传1-已定价则返回6-定价成功
     */
    List<DataToken> selectByPricingStatus(@Param("status") int status, @Param("address") String address);

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

    DataToken selectById(Integer dataTokenId);

    List<DataToken> selectListByStatus(int status);

    int updateTokenAddress(@Param("id") int id, @Param("tokenAddress") String tokenAddress);
}
