package com.platon.metis.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 
 * 全网算力资源表 记录全网的算力资源信息
 */
@Getter
@Setter
@ToString
public class GlobalPower implements Serializable {
    private String id;

    private String identityId;

    private Long memory;

    private Integer core;

    private Long bandwidth;

    private Long usedMemory;

    private Integer usedCore;

    private Long usedBandwidth;

    private Boolean published;

    private LocalDateTime publishAt;

    private Integer status;

    private LocalDateTime updateAt;
}