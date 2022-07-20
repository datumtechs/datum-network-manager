package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.dto.DataAuthReqDTO;
import com.platon.datum.admin.dao.dto.UsedResourceDTO;

import java.util.List;
import java.util.Map;

/**
 * @author houz
 */
public interface VLocalStatsMapper {

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
     * 查询待授权数据列表
     *
     * @return
     */
    List<DataAuthReqDTO> listDataAuthReqWaitingForApprove();


}