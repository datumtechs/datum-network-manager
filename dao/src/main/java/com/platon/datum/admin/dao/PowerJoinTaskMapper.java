package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.PowerJoinTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author houz
 * 业务节点实体类
 */
@Repository
public interface PowerJoinTaskMapper {

    /**
     * 插入计算节点参与任务列表
     * @param powerJoinTaskList
     * @return
     */
    int insertBatch(@Param("powerJoinTaskList")  List<PowerJoinTask> powerJoinTaskList);

}