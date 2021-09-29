package com.platon.metis.admin.dao;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface SyncCheckpointMapper {
    int updateMetadataAuth(@Param("checkpoint") LocalDateTime checkpoint);
    int updateOrg(@Param("checkpoint") LocalDateTime checkpoint);
    int updateMetadata(@Param("checkpoint") LocalDateTime checkpoint);
    int updatePower(@Param("checkpoint") LocalDateTime checkpoint);
    int updateTask(@Param("checkpoint") LocalDateTime checkpoint);
}
