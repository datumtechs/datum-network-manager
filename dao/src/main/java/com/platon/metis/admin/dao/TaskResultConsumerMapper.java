package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.TaskResultConsumer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskResultConsumerMapper {
    int deleteByPrimaryKey(@Param("taskId") String taskId, @Param("consumerIdentityId") String consumerIdentityId, @Param("producerIdentityId") String producerIdentityId);

    int insert(TaskResultConsumer record);

    int insertSelective(TaskResultConsumer record);

    TaskResultConsumer selectByPrimaryKey(@Param("taskId") String taskId, @Param("consumerIdentityId") String consumerIdentityId, @Param("producerIdentityId") String producerIdentityId);

    List<TaskResultConsumer> selectTaskResultWithOrgByTaskId(@Param("taskId") String taskId);

    int insertBatch(List<TaskResultConsumer> resultReceiverList);

}