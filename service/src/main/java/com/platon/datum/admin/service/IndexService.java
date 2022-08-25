package com.platon.datum.admin.service;

import com.platon.datum.admin.dao.dto.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2021/7/2 17:10
 * @Version
 * @Desc
 */
public interface IndexService {

    /**
     * 查询总计算资源占用情况
     *
     * @return
     */
    UsedResourceDTO queryUsedTotalResource();


    /**
     * 查询我的计算任务概况
     *
     * @return
     */
    List<Map<String, Object>> queryMyCalculateTaskStats(String userAddress, boolean isAdmin);

    /**
     * 查询数据待授权列表
     *
     * @return
     */
    List<DataAuthReqDTO> listDataAuthReqWaitingForApprove(String userAddress);

    /**
     * 查询本地数据总量月走势
     *
     * @return
     */
    List<StatsDataTrendDTO> listLocalDataFileStatsTrendMonthly(String userAddress);

    /**
     * 查询本地算力（内存）总量月走势
     *
     * @return
     */
    List<StatsPowerTrendDTO> listLocalPowerStatsTrendMonthly();

    /**
     * @return
     */
    DataTokenOverviewDTO listDataTokenOverview(String currentUserAddress);

}
