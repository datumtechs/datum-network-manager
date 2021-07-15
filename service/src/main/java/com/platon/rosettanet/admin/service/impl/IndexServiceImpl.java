package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.VLocalStatsMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dao.entity.VLocalStats;
import com.platon.rosettanet.admin.service.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        List<LocalPowerNode> localPowerNodes = localPowerNodeMapper.queryPowerNodeList(identityId, null);
        return localPowerNodes;
    }
}
