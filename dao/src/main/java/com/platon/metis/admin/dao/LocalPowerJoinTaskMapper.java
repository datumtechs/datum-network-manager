package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalPowerJoinTask;
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
    int insertBatch(@Param("localPowerJoinTaskList")  List<LocalPowerJoinTask> localPowerJoinTaskList);

    /**
     * 查询计算节点参数任务列表
     * @param nodeId
     * @return
     */
    //List<LocalPowerJoinTask> listRunningTaskByPowerNodeId(@Param(value = "nodeId") String nodeId);

    /**
     * 清空表数据
     * @return
     */
    int truncateTable();

}