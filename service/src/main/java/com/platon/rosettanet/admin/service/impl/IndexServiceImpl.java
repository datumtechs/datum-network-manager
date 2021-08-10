package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.VLocalStatsMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dao.entity.VLocalStats;
import com.platon.rosettanet.admin.service.IndexService;
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

    @Override
    public VLocalStats getOverview() {
        VLocalStats vLocalStats = localStatsMapper.selectLocalStats();
        return vLocalStats;
    }

    @Override
    public List<LocalPowerNode> getPowerNodeList() {
        String identityId = LocalOrgIdentityCache.getIdentityId();
        List<LocalPowerNode> localPowerNodes = localPowerNodeMapper.queryPowerNodeList(identityId);
        return localPowerNodes;
    }

    @Override
    public Map queryUsedTotalResource() {
        return localStatsMapper.queryUsedTotalResource();
    }

    @Override
    public Map queryMyPublishData() {
        return localStatsMapper.queryMyPublishData();
    }

    @Override
    public Map queryMyPowerTaskStats() {
        return localStatsMapper.queryMyPowerTaskStats();
    }

    @Override
    public Map queryWholeNetDateAndPower(String flag) {
        // 查询全网数据走势
        if ("1".equals(flag)) {
           return localStatsMapper.queryWholeNetDateTrend();
        }
        // 查询全网算力走势
        if ("2".equals(flag)) {
            return localStatsMapper.queryWholeNetPowerTrend();
        }
        return new HashMap(2);
    }

    @Override
    public Map queryWholeNetDateTotalRatio() {
        return localStatsMapper.queryWholeNetDateTotalRatio();
    }
}
