package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.TaskResultConsumer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskResultConsumerMapper {
    int deleteByPrimaryKey(@Param("taskId") String taskId, @Param("consumerIdentityId") String consumerIdentityId, @Param("producerIdentityId") String producerIdentityId);

    int insert(TaskResultConsumer record);

    int insertSelective(TaskResultConsumer record);

    TaskResultConsumer selectByPrimaryKey(@Param("taskId") String taskId, @Param("consumerIdentityId") String consumerIdentityId, @Param("producerIdentityId") String producerIdentityId);

    List<TaskResultConsumer> selectTaskResultWithOrgByTaskId(@Param("taskId") String taskId);

    int replaceBatch(List<TaskResultConsumer> resultReceiverList);

}