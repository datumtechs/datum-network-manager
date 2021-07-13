package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TaskPowerProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskPowerProviderMapper {
    int deleteByPrimaryKey(@Param("taskId") String taskId, @Param("identityId") String identityId);

    int insert(TaskPowerProvider record);

    int insertSelective(TaskPowerProvider record);

    TaskPowerProvider selectByPrimaryKey(@Param("taskId") String taskId, @Param("identityId") String identityId);

    int updateByPrimaryKeySelective(TaskPowerProvider record);

    int updateByPrimaryKey(TaskPowerProvider record);

    List<TaskPowerProvider>  selectTaskPowerWithOrgByTaskId(@Param("taskId") String taskId);

    int batchUpdate(List<TaskPowerProvider> powerProviderList);

    int insertBatch(List<TaskPowerProvider> powerProviderList);

}