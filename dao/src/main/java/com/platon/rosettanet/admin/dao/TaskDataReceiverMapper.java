package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TaskDataReceiver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskDataReceiverMapper {
    int deleteByPrimaryKey(@Param("taskId") String taskId, @Param("metaDataId") String metaDataId);

    int insert(TaskDataReceiver record);

    int insertSelective(TaskDataReceiver record);

    TaskDataReceiver selectByPrimaryKey(@Param("taskId") String taskId, @Param("metaDataId") String metaDataId);

    int updateByPrimaryKeySelective(TaskDataReceiver record);

    int updateByPrimaryKey(TaskDataReceiver record);

    List<TaskDataReceiver> selectTaskDataWithOrgByTaskId(@Param("taskId") String taskId);
}