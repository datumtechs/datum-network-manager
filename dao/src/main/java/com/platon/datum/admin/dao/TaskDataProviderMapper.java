package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.TaskDataProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskDataProviderMapper {

    List<TaskDataProvider> selectTaskDataWithOrgByTaskId(@Param("taskId") String taskId);

    int replaceBatch(List<TaskDataProvider> dataReceiverList);




}