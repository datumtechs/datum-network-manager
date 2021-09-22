package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalPowerHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author houz
 * 业务节点实体类
 */
@Repository
public interface LocalPowerHistoryMapper {

    /**
     * 插入计算节点资源详情
     * @param localPowerHistory
     * @return
     */
    int insertPowerHistory(LocalPowerHistory localPowerHistory);

    /**
     * 批量插入计算节点资源详情
     * @param localPowerHistoryList
     * @return
     */
    int batchInsertPowerHistory(List<LocalPowerHistory> localPowerHistoryList);

    /**
     * 查询计算节点资源详情
     * @param powerNodeId
     * @param refreshStatus
     * @return
     */
    List<LocalPowerHistory> queryPowerHistory(@Param(value = "powerNodeId") String powerNodeId,
                                              @Param(value = "refreshStatus") String refreshStatus);


    /**
     * 查询某一天的计算历史资源
     * @param powerNodeId
     * @param refreshStatus
     * @param timeFlag
     * @return
     */
    Map queryPowerHistoryDay(@Param(value = "powerNodeId") String powerNodeId,
                             @Param(value = "refreshStatus") String refreshStatus,
                             @Param(value = "timeFlag") String timeFlag);
}