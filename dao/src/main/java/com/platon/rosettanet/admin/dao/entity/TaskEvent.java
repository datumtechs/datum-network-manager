package com.platon.rosettanet.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Setter
@Getter
@ToString
public class TaskEvent {

    private Integer id;

    private String type;

    private String taskId;

    private Owner owner;

    private String content;

    private LocalDateTime createAt;

    private String ownerIdentity;

    private String ownerName;

    private LocalDateTime lastUpdateTime;



}
