package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.DataToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataTokenMapper {

    /**
     * 查询已定价和未定价的dataToken
     * 状态：0-未定价，1-已定价，如果要已定价的，则返回已成功定价的，如果要未定价的，则返回(0-未定价，1-定价中，2-定价失败)
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
    void updatePricingStatus(DataToken dataToken);

    DataToken selectById(Integer dataTokenId);
}
