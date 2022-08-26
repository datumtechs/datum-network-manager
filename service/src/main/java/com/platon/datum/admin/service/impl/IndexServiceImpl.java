package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.dao.AttributeDataTokenMapper;
import com.platon.datum.admin.dao.DataTokenMapper;
import com.platon.datum.admin.dao.StatsTrendMapper;
import com.platon.datum.admin.dao.VLocalStatsMapper;
import com.platon.datum.admin.dao.dto.*;
import com.platon.datum.admin.service.IndexService;
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
    public List<Map<String, Object>> queryMyCalculateTaskStats(String userAddress, boolean isAdmin) {
        if(isAdmin){
            return localStatsMapper.queryAdminCalculateTaskStats(userAddress);
        } else {
            return localStatsMapper.queryMyCalculateTaskStats(userAddress);
        }
    }

    @Override
    public List<DataAuthReqDTO> listDataAuthReqWaitingForApprove(String userAddress) {
        return localStatsMapper.listDataAuthReqWaitingForApprove(userAddress);
    }

    @Override
    public List<StatsDataTrendDTO> listLocalDataFileStatsTrendMonthly(String userAddress) {
        return statsTrendMapper.listLocalDataFileStatsTrendMonthly(userAddress);
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
    public DataTokenOverviewDTO listDataTokenOverview(String currentUserAddress) {
        long pricedCount  = dataTokenMapper.countByUserAndPriced(currentUserAddress);
        long unPricedCount  = dataTokenMapper.countByUserAndUnPriced(currentUserAddress);
        long attributeDataTokenCount = attributeDataTokenMapper.countByUser(currentUserAddress);
        DataTokenOverviewDTO dataTokenOverviewDTO = new DataTokenOverviewDTO();
        dataTokenOverviewDTO.setPricedDataTokenCount(pricedCount);
        dataTokenOverviewDTO.setUnPriceddataTokenCount(unPricedCount);
        dataTokenOverviewDTO.setAttributeDataTokenCount(attributeDataTokenCount);
        return dataTokenOverviewDTO;
    }

}
