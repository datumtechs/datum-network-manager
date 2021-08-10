package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dao.entity.VLocalStats;

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
     * 获取本组织的统计数据
     * @return
     */
    VLocalStats getOverview();

    /**
     * 获取本组织计算节点列表信息
     */
    List<LocalPowerNode> getPowerNodeList();

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
     * 查询全网数据或算力总量走势
     * @param flag
     * @return
     */
    Map queryWholeNetDateAndPower(String flag);

    /**
     * 查询全网数据总量环比
     * @return
     */
    Map queryWholeNetDateTotalRatio();
}
