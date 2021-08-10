package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.VLocalStatsMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dao.entity.VLocalStats;
import com.platon.rosettanet.admin.service.IndexService;
import com.platon.rosettanet.admin.service.constant.ServiceConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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
    private LocalPowerNodeMapper localPowerNodeMapper;

//    @Override
//    public VLocalStats getOverview() {
//        VLocalStats vLocalStats = localStatsMapper.selectLocalStats();
//        return vLocalStats;
//    }

//    @Override
//    public List<LocalPowerNode> getPowerNodeList() {
//        String identityId = LocalOrgIdentityCache.getIdentityId();
//        List<LocalPowerNode> localPowerNodes = localPowerNodeMapper.queryPowerNodeList(identityId);
//        return localPowerNodes;
//    }

    @Override
    public Map<String, Object> queryUsedTotalResource() {
        return localStatsMapper.queryUsedTotalResource();
    }

    @Override
    public Map<String, Object> queryMyPublishData() {
        return localStatsMapper.queryMyPublishData();
    }

    @Override
    public Map<String, Object> queryMyPowerTaskStats() {
        return localStatsMapper.queryMyPowerTaskStats();
    }

    @Override
    public Map<String, Object> queryWholeNetDateAndPower(String flag) {
        // 查询全网数据走势
        if (ServiceConstant.constant_1.equals(flag)) {
           return localStatsMapper.queryWholeNetDateTrend();
        }
        // 查询全网算力走势
        if (ServiceConstant.constant_1.equals(flag)) {
            return localStatsMapper.queryWholeNetPowerTrend();
        }
        return new HashMap(2);
    }

    @Override
    public Map<String, Object> queryWholeNetDateTotalRatio() {
        Map<String, Object> map = new HashMap(4);
        // 查询计算周环比
        List<Float> weekList = localStatsMapper.queryWholeNetDateWeekRatio();
        String weekRatio = this.calculateRatio(weekList);
        // 查询计算月环比
        List<Float> monthList = localStatsMapper.queryWholeNetDateMonthRatio();
        String monthRatio = this.calculateRatio(monthList);
        // 周环比
        map.put("weekRatio", weekRatio);
        // 月环比
        map.put("monthRatio", monthRatio);
        return map;
    }

    /** 计算环比 */
    private String calculateRatio(List<Float> list){
        String ratio = "0.0";
        if (list == null || list.size() == 0) {
            return ratio;
        }
        if (list.size() == ServiceConstant.integer_1) {
            return String.valueOf(list.get(0));
        }
        if (list.size() >= ServiceConstant.integer_2) {
            return String.format("%.1f", list.get(0)/list.get(1));
        }
        return ratio;
    }
}
