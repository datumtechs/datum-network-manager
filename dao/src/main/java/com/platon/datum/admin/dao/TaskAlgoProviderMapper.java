package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.TaskAlgoProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskAlgoProviderMapper {
    TaskAlgoProvider selectByPrimaryKey(@Param("taskId") String taskId);

    void insert(TaskAlgoProvider taskAlgoProvider);

    void replaceBatch(List<TaskAlgoProvider> taskAlgoProviderList);

    TaskAlgoProvider selectTaskAlgoProviderWithOrgNameByTaskId(String taskId);
}
