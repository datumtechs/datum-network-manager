package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.VLocalStats;
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
    private Integer dataNodeCount;//数据节点数量
    private Integer powerNodeCount;//计算节点数量
    private Integer publishedDataCount;//数据节点已发布数据总量
    private Integer unpublishedDataCount;//数据节点未发布数据总量
    private Integer runningTaskCount;//计算节点正在执行任务总数
    private Integer totalProcessor;//计算资源提供CPU总核数
    private Integer usedProcessor;//计算资源使用CPU总核数
    private Long totalMem;//计算资源内存提供总量，单位：byte
    private Long usedMem;//计算资源内存已使用总量，单位：byte
    private Long totalBandwidth;//计算资源带宽总量，单位：byte
    private Long usedBandwidth;//计算资源贷款已使用总量，单位：byte

    public static IndexOverviewResp from(VLocalStats localStats) {
        IndexOverviewResp resp = new IndexOverviewResp();
        resp.setStatus(localStats.getCarrierConnStatus());
        resp.setDataNodeCount(localStats.getDataNodeCount());
        resp.setPowerNodeCount(localStats.getPowerNodeCount());
        resp.setPublishedDataCount(localStats.getReleasedDataFileCount());
        resp.setUnpublishedDataCount(localStats.getUnreleasedDataFileCount());
        resp.setRunningTaskCount(localStats.getTaskCount());
        resp.setTotalProcessor(localStats.getTotalCore());
        resp.setUsedProcessor(localStats.getUsedCore());
        resp.setTotalMem(localStats.getTotalMemory());
        resp.setUsedMem(localStats.getUsedMemory());
        resp.setTotalBandwidth(localStats.getTotalBandwidth());
        resp.setUsedBandwidth(localStats.getUsedBandwidth());
        return resp;
    }
}
