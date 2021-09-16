package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.StoreCalculateResult;

/**
 * @author houz
 */
public interface StoreCalculateResultMapper {

    /**
     * 保存计算结果
     * @param storeCalculateResult
     * @return
     */
    int saveMyPublishDataMonth(StoreCalculateResult storeCalculateResult);

    /**
     * 查询我发布的上月总数据
     * @return
     */
    Long queryMyMonthPublishData();

}