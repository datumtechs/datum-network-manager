package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.dto.UsedResourceDTO;

import java.util.List;
import java.util.Map;

/**
 * @author houz
 */
public interface VLocalStatsMapper {

    /**
     * 查询总计算资源占用情况
     * @return
     */
    UsedResourceDTO queryUsedTotalResource();

    /**
     * 查询我发布的数据
     * @return
     */
    List<Long> queryMyPublishData();

    /**
     * 查询我发布的算力
     * @return
     */
    List<Long> queryMyPublishPower();

    /**
     * 查询我的计算任务概况
     * @return
     */
    List<Map<String, Object>> queryMyCalculateTaskStats();

    /**
     * 查询全网数据总量走势
     * @return
     */
    List<Long> queryWholeNetDateTrend();

    /**
     * 查询全网数据总量走势
     * @return
     */
    List<Long> queryWholeNetPowerTrend();

    /**
     * 查询全网数据总量月环比
     * @return
     */
    List<Double> queryWholeNetDateRingRatio();

    /**
     * 查询全网数据总量月同比
     * @return
     */
    List<Double> queryWholeNetDateSameRatio();

    /**
     * 查询待授权数据列表
     * @return
     */
    List<Map<String, Object>> queryWaitAuthDataList();


}