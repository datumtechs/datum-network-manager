package com.platon.rosettanet.admin.dao.entity;

import com.platon.rosettanet.admin.dao.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 全网算力资源表 记录全网的算力资源信息
 */
@Getter
@Setter
@ToString
public class GlobalPower extends BaseDomain implements Serializable {
    /**
     * 序号
     */
    private Integer id;

    /**
     * 算力提供方身份标识
     */
    private String identityId;

    /**
     * 总CPU
     */
    private Integer totalCore;

    /**
     * 总内存
     */
    private Long totalMemory;

    /**
     * 总带宽
     */
    private Long totalBandwidth;

    /**
     * 已使用CPU信息
     */
    private Integer usedCore;

    /**
     * 已使用内存
     */
    private Long usedMemory;

    /**
     * 已使用带宽
     */
    private Long usedBandwidth;

    /**
     * 最后更新时间
     */
    private Date recUpdateTime;

    private static final long serialVersionUID = 1L;
}