package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalPowerHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * @return
     */
    List<LocalPowerHistory> queryPowerHistory(String powerNodeId);

}