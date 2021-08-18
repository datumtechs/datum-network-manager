package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;

/**
 * 任务信息数量统计
 */
@Data
public class TaskStatistics {

    //任务总数
    private int totalTaskCount;
    //任务成功数量
    private int taskSuccessCount;
    //任务失败数量
    private int taskFailedCount;
    //任务等待中数量
    private int taskPendingCount;
    //任务计算中数量
    private int taskRunningCount;
    //任务发起方数量
    private int ownerCount;
    //数据提供方数量
    private int dataSupplierCount;
    //算力提供方
    private int powerSupplierCount;
    //结果接收方
    private int receiverCount;
    //算法提供方
    private int algoSupplierCount;



}
