package com.platon.datum.admin.service;

import com.platon.datum.admin.dao.dto.DataAuthReqDTO;
import com.platon.datum.admin.dao.dto.StatsDataTrendDTO;
import com.platon.datum.admin.dao.dto.StatsPowerTrendDTO;
import com.platon.datum.admin.dao.dto.UsedResourceDTO;

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
    List<Map<String, Object>> queryMyCalculateTaskStats();

    /**
     * 查询数据待授权列表
     *
     * @return
     */
    List<DataAuthReqDTO> listDataAuthReqWaitingForApprove();

    /**
     * 查询本地数据总量月走势
     *
     * @return
     */
    List<StatsDataTrendDTO> listLocalDataFileStatsTrendMonthly();

    /**
     * 查询本地算力（内存）总量月走势
     *
     * @return
     */
    List<StatsPowerTrendDTO> listLocalPowerStatsTrendMonthly();

}
