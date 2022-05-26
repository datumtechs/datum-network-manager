package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.LocalPowerJoinTask;
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

}