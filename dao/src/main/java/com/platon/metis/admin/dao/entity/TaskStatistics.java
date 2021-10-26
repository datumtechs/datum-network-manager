package com.platon.metis.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务信息数量统计
 */
@Data

@ApiModel(value = "我参与的任务情况统计")
public class TaskStatistics {

    //任务成功数量
    @ApiModelProperty(name = "totalCount", value = "我参与的任务成功数量")
    private int successCount;
    //任务失败数量
    @ApiModelProperty(name = "totalCount", value = "我参与的任务失败数量")
    private int failedCount;
    //任务等待中数量
    @ApiModelProperty(name = "totalCount", value = "我参与的任务等待数量")
    private int pendingCount;
    //任务计算中数量
    @ApiModelProperty(name = "totalCount", value = "我参与的任务计算中数量")
    private int runningCount;
    //任务发起方数量
    @ApiModelProperty(name = "totalCount", value = "我作为发起方的任务数量")
    private int ownerCount;
    //数据提供方数量
    @ApiModelProperty(name = "totalCount", value = "我作为数据提供者的任务数量")
    private int dataProviderCount;
    //算力提供方
    @ApiModelProperty(name = "totalCount", value = "我作为算力提供者的任务数量")
    private int powerProviderCount;
    //结果接收方
    @ApiModelProperty(name = "totalCount", value = "我作为结果接收者的任务数量")
    private int resultReceiverCount;
    //算法提供方
    @ApiModelProperty(name = "totalCount", value = "我作为是否提供者的任务数量")
    private int algoProviderCount;



}
