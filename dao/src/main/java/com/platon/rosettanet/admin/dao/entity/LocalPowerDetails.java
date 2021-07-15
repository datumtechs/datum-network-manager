package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;
import java.util.Date;

/**
 * @author houz
 * 计算节点实体类
 */
@Data
public class LocalPowerDetails {

    private Integer id;

    private String powerNodeId;

    private String refreshStatus;

    private Long usedMemory;

    private Integer usedCore;

    private Long usedBandwidth;

    private Date createTime;

    private Date updateTime;

}