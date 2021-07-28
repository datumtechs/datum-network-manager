package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.VLocalStats;
import com.platon.rosettanet.admin.dao.enums.CarrierConnStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class IndexOverviewResp {

    @ApiModelProperty(name = "status", value = "调度服务状态：enabled 开启 disabled 关闭")
    private String status;//调度服务状态：1 开启 0 关闭
    @ApiModelProperty(name = "dataNodeCount", value = "数据节点数量")
    private Integer dataNodeCount;//数据节点数量
    @ApiModelProperty(name = "powerNodeCount", value = "计算节点数量")
    private Integer powerNodeCount;//计算节点数量
    @ApiModelProperty(name = "publishedDataCount", value = "数据节点已发布数据总量")
    private Integer publishedDataCount;//数据节点已发布数据总量
    @ApiModelProperty(name = "unpublishedDataCount", value = "数据节点未发布数据总量")
    private Integer unpublishedDataCount;//数据节点未发布数据总量
    @ApiModelProperty(name = "runningTaskCount", value = "计算节点正在执行任务总数")
    private Integer runningTaskCount;//计算节点正在执行任务总数
    @ApiModelProperty(name = "totalProcessor", value = "计算资源提供CPU总核数")
    private Integer totalProcessor;//计算资源提供CPU总核数
    @ApiModelProperty(name = "usedProcessor", value = "计算资源使用CPU总核数")
    private Integer usedProcessor;//计算资源使用CPU总核数
    @ApiModelProperty(name = "totalMem", value = "计算资源内存提供总量，单位：byte")
    private Long totalMem;//计算资源内存提供总量，单位：byte
    @ApiModelProperty(name = "usedMem", value = "计算资源内存已使用总量，单位：byte")
    private Long usedMem;//计算资源内存已使用总量，单位：byte
    @ApiModelProperty(name = "totalBandwidth", value = "计算资源带宽总量，单位：byte")
    private Long totalBandwidth;//计算资源带宽总量，单位：byte
    @ApiModelProperty(name = "usedBandwidth", value = "计算资源带宽已使用总量，单位：byte")
    private Long usedBandwidth;//计算资源带宽已使用总量，单位：byte

    public static IndexOverviewResp from(VLocalStats localStats) {
        if(localStats == null){
            return null;
        }
        IndexOverviewResp resp = new IndexOverviewResp();
        if(CarrierConnStatusEnum.ENABLED.getStatus().equals(localStats.getCarrierConnStatus())){
            resp.setStatus(CarrierConnStatusEnum.ENABLED.getStatus());
        } else {
            resp.setStatus(CarrierConnStatusEnum.DISABLED.getStatus());
        }
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
