package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.TaskResultReceiver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskResultReceiverMapper {
    int deleteByPrimaryKey(@Param("taskId") String taskId, @Param("consumerIdentityId") String consumerIdentityId, @Param("producerIdentityId") String producerIdentityId);

    int insert(TaskResultReceiver record);

    int insertSelective(TaskResultReceiver record);

    TaskResultReceiver selectByPrimaryKey(@Param("taskId") String taskId, @Param("consumerIdentityId") String consumerIdentityId, @Param("producerIdentityId") String producerIdentityId);

    List<TaskResultReceiver> selectTaskResultWithOrgByTaskId(@Param("taskId") String taskId);


    int updateByPrimaryKeySelective(TaskResultReceiver record);

    int updateByPrimaryKey(TaskResultReceiver record);

    int batchUpdate(List<TaskResultReceiver> resultReceiverList);

    int insertBatch(List<TaskResultReceiver> resultReceiverList);

}