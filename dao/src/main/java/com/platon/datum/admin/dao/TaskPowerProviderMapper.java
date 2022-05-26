package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.TaskPowerProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskPowerProviderMapper {
    int deleteByPrimaryKey(@Param("taskId") String taskId, @Param("identityId") String identityId);

    int insert(TaskPowerProvider record);

    TaskPowerProvider selectByPrimaryKey(@Param("taskId") String taskId, @Param("identityId") String identityId);

    int updateByPrimaryKey(TaskPowerProvider record);

    List<TaskPowerProvider>  selectTaskPowerWithOrgByTaskId(@Param("taskId") String taskId);

    int batchUpdate(List<TaskPowerProvider> powerProviderList);

    int replaceBatch(List<TaskPowerProvider> powerProviderList);

}