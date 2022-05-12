package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.dao.StatsTrendMapper;
import com.platon.metis.admin.dao.VLocalStatsMapper;
import com.platon.metis.admin.dao.dto.DataAuthReqDTO;
import com.platon.metis.admin.dao.dto.StatsDataTrendDTO;
import com.platon.metis.admin.dao.dto.StatsPowerTrendDTO;
import com.platon.metis.admin.dao.dto.UsedResourceDTO;
import com.platon.metis.admin.service.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2021/7/2 17:11
 * @Version
 * @Desc
 */

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private VLocalStatsMapper localStatsMapper;
    @Resource
    private StatsTrendMapper statsTrendMapper;

    @Override
    public UsedResourceDTO queryUsedTotalResource() {
        UsedResourceDTO usedResourceDTO = localStatsMapper.queryUsedTotalResource();
        return usedResourceDTO;
    }

    @Override
    public List<Map<String, Object>> queryMyCalculateTaskStats() {
        return localStatsMapper.queryMyCalculateTaskStats();
    }

    @Override
    public List<DataAuthReqDTO> listDataAuthReqWaitingForApprove() {
        return localStatsMapper.listDataAuthReqWaitingForApprove();
    }

    @Override
    public List<StatsDataTrendDTO> listLocalDataFileStatsTrendMonthly() {
        return statsTrendMapper.listLocalDataFileStatsTrendMonthly();
    }

    @Override
    public List<StatsPowerTrendDTO> listLocalPowerStatsTrendMonthly() {
        return statsTrendMapper.listLocalPowerStatsTrendMonthly();
    }

}
