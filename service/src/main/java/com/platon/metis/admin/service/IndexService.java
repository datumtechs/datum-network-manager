package com.platon.metis.admin.service;

import com.platon.metis.admin.dao.dto.UsedResourceDTO;

import java.util.List;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2021/7/2 17:10
 * @Version
 * @Desc
 */
public interface IndexService {

//    /**
//     * 获取本组织的统计数据
//     * @return
//     */
//    VLocalStats getOverview();
//
//    /**
//     * 获取本组织计算节点列表信息
//     */
//    List<LocalPowerNode> getPowerNodeList();

    /**
     * 查询总计算资源占用情况
     * @return
     */
    UsedResourceDTO queryUsedTotalResource();

    /**
     * 查询我发布的数据和算力
     * @param flag 请求标志
     * @return
     */
    List<Long> queryPublishDataOrPower(String flag);

    /**
     * 查询我的计算任务概况
     * @return
     */
    List<Map<String, Object>> queryMyCalculateTaskStats();

    /**
     * 查询全网数据或算力总量走势
     * @param flag
     * @return
     */
    List<Long> queryWholeNetDateOrPower(String flag);

    /**
     * 查询全网数据总量环比
     * @return
     */
    Map<String, Object> queryWholeNetDateTotalRatio();

    /**
     * 查询数据待授权列表
     * @return
     */
    List<Map<String, Object>> queryWaitAuthDataList();
}
