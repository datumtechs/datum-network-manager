package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.dao.AttributeDataTokenMapper;
import com.platon.datum.admin.dao.DataTokenMapper;
import com.platon.datum.admin.dao.StatsTrendMapper;
import com.platon.datum.admin.dao.VLocalStatsMapper;
import com.platon.datum.admin.dao.dto.DataAuthReqDTO;
import com.platon.datum.admin.dao.dto.StatsDataTrendDTO;
import com.platon.datum.admin.dao.dto.StatsPowerTrendDTO;
import com.platon.datum.admin.dao.dto.UsedResourceDTO;
import com.platon.datum.admin.service.IndexService;
import org.apache.commons.lang3.tuple.Pair;
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
    @Resource
    private DataTokenMapper dataTokenMapper;
    @Resource
    private AttributeDataTokenMapper attributeDataTokenMapper;

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

    /**
     * left 是无属性凭证数量
     * right 有属性凭证数量
     *
     * @return
     */
    @Override
    public Pair<Long, Long> listDataTokenOverview() {
        long dataTokenCount = dataTokenMapper.count();
        long attributeDataTokenCount = attributeDataTokenMapper.count();
        return Pair.of(dataTokenCount,attributeDataTokenCount);
    }

}
