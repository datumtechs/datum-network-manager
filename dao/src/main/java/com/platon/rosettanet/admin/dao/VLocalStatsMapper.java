package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.VLocalStats;

import java.util.Map;

public interface VLocalStatsMapper {

    VLocalStats selectLocalStats();

    /**
     * 查询总计算资源占用情况
     * @return
     */
    Map queryUsedTotalResource();

    /**
     * 查询我发布的数据
     * @return
     */
    Map queryMyPublishData();

    /**
     * 查询我的计算任务概况
     * @return
     */
    Map queryMyPowerTaskStats();

    /**
     * 查询全网数据总量走势
     * @return
     */
    Map queryWholeNetDateTrend();

    /**
     * 查询全网数据总量走势
     * @return
     */
    Map queryWholeNetPowerTrend();

    /**
     * 查询全网数据总量环比
     * @return
     */
    Map queryWholeNetDateTotalRatio();
}