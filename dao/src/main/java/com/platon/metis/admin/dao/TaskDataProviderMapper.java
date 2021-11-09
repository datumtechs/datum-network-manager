package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.TaskDataProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskDataProviderMapper {
    int deleteByPrimaryKey(@Param("taskId") String taskId, @Param("metaDataId") String metaDataId);

    int insert(TaskDataProvider record);

    TaskDataProvider selectByPrimaryKey(@Param("taskId") String taskId, @Param("metaDataId") String metaDataId);

    List<TaskDataProvider> selectTaskDataWithOrgByTaskId(@Param("taskId") String taskId);

    int insertBatch(List<TaskDataProvider> dataReceiverList);




}