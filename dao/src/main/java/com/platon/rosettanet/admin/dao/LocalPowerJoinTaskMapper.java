package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalPowerDetails;
import com.platon.rosettanet.admin.dao.entity.LocalPowerJoinTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author houz
 * 业务节点实体类
 */
@Repository
public interface LocalPowerJoinTaskMapper {

    /**
     * 插入计算节点参与任务列表
     * @param localPowerJoinTaskList
     * @return
     */
    int batchInsertPowerTask(List<LocalPowerJoinTask> localPowerJoinTaskList);

    /**
     * 查询计算节点参数任务列表
     * @param powerNodeId
     * @return
     */
    List<LocalPowerJoinTask> queryPowerJoinTaskList(@Param(value = "powerNodeId") String powerNodeId);

}