package com.platon.metis.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 
 * VIEW
 */
@Getter
@Setter
@ToString
public class VLocalStats implements Serializable {
    /**
     * 连接状态 enabled：可用, disabled:不可用
     */
    private String carrierConnStatus;

    private Integer dataNodeCount;

    private Integer powerNodeCount;

    private Integer totalCore;

    private Long totalMemory;

    private Long totalBandwidth;

    private Integer usedCore;

    private Long usedMemory;

    private Long usedBandwidth;

    private Integer releasedDataFileCount;

    private Integer unreleasedDataFileCount;

    private Integer taskCount;

    private static final long serialVersionUID = 1L;
}