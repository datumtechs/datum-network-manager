package com.platon.rosettanet.admin.dto.resp;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:59
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class IndexNodeListResp {

    private String jobNodeId;//计算节点ID
    private String jobNodeName;//计算节点名称
    private String status;//计算节点状态：计算中、空闲中
    private Integer totalProcessor;//计算节点提供的CPU总量
    private Integer usedProcessor;//计算节点已使用的CPU总量
    private Long totalMem;//计算节点提供的内存总量，单位：byte
    private Long usedMem;//计算节点已使用的内存总量，单位：byte
    private Long totalBandwidth;//计算节点提供的带宽总量，单位：byte
    private Long usedBandwidth;//计算节点已使用的贷款总量，单位：byte
    private Integer startTime;//计算节点的启动时间，单位：秒级时间戳

    public static IndexNodeListResp from(LocalPowerNode localPowerNode) {
        IndexNodeListResp resp = new IndexNodeListResp();
        resp.setJobNodeId(localPowerNode.getPowerNodeId());
        resp.setJobNodeName(localPowerNode.getPowerNodeName());
        resp.setStatus(localPowerNode.getStatus());
        resp.setTotalProcessor(localPowerNode.getCore());
        resp.setUsedProcessor(localPowerNode.getUsedCore());
        resp.setTotalMem(localPowerNode.getMemory());
        resp.setUsedMem(localPowerNode.getUsedMemory());
        resp.setTotalBandwidth(localPowerNode.getBandwidth());
        resp.setUsedBandwidth(localPowerNode.getUsedBandwidth());
        resp.setStartTime(localPowerNode.getStartTime().getSecond());
        return resp;
    }
}
