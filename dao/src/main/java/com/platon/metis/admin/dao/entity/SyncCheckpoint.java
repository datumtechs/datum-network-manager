package com.platon.metis.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SyncCheckpoint {
    private LocalDateTime metadataAuth;
    private LocalDateTime org;
    private LocalDateTime metadata;
    private LocalDateTime power;
    private LocalDateTime task;
}
