package com.platon.rosettanet.admin.dto.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:49
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class IndexOverviewResp {

    private String status;//调度服务状态：N 开启 D 关闭
    private int dataNodeCount;//数据节点数量
    private int powerNodeCount;//计算节点数量
    private int publishedDataCount;//数据节点已发布数据总量
    private int unpublishedDataCount;//数据节点未发布数据总量
    private int runningTaskCount;//计算节点正在执行任务总数
    private int totalProcessor;//计算资源提供CPU总核数
    private int usedProcessor;//计算资源使用CPU总核数
    private int totalMem;//计算资源内存提供总量，单位：byte
    private int usedMem;//计算资源内存已使用总量，单位：byte
    private int totalBandwidth;//计算资源带宽总量，单位：byte
    private int usedBandwidth;//计算资源贷款已使用总量，单位：byte
}
