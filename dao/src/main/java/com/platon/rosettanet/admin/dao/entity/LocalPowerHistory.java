package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;
import java.util.Date;

/**
 * @author houz
 * 计算节点实体类
 */
@Data
public class LocalPowerHistory {

    /** 序号id */
    private Integer id;

    /** 计算节点id */
    private String powerNodeId;

    /** 数据刷新时间 */
    private String refreshStatus;

    /** 已使用内存 */
    private Long usedMemory;

    /** 已使用核数 */
    private Integer usedCore;

    /** 已使用带宽 */
    private Long usedBandwidth;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}