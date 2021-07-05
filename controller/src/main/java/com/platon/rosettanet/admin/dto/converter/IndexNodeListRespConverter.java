package com.platon.rosettanet.admin.dto.converter;

import com.platon.rosettanet.admin.dao.entity.TbPowerNode;
import com.platon.rosettanet.admin.dto.resp.IndexNodeListResp;

/**
 * @Author liushuyu
 * @Date 2021/7/3 15:08
 * @Version
 * @Desc
 */
public class IndexNodeListRespConverter {

    public static IndexNodeListResp convert(TbPowerNode tbPowerNode){
        IndexNodeListResp indexNodeListResp = new IndexNodeListResp();
        private String jobNodeId;//计算节点ID
        private String jobNodeName;//计算节点名称
        private String status;//计算节点状态：计算中、空闲中
        private int totalProcessor;//计算节点提供的CPU总量
        private int usedProcessor;//计算节点已使用的CPU总量
        private int totalMem;//计算节点提供的内存总量，单位：byte
        private int usedMem;//计算节点已使用的内存总量，单位：byte
        private int totalBandwidth;//计算节点提供的带宽总量，单位：byte
        private int usedBandwidth;//计算节点已使用的贷款总量，单位：byte
        private int duration;//计算节点的运行时长，单位：秒级时间戳
        private int startTime;//计算节点的启动时间，单位：秒级时间戳
        indexNodeListResp.setJobNodeId(tbPowerNode.getNodeId());
        indexNodeListResp.setJobNodeName(tbPowerNode.getNodeName());
        indexNodeListResp.setStatus("");
        indexNodeListResp.setTotalProcessor();
        indexNodeListResp.setUsedProcessor();
        indexNodeListResp.setTotalMem();
        indexNodeListResp.setUsedMem();
        indexNodeListResp.setTotalBandwidth();
        indexNodeListResp.setUsedBandwidth();
        indexNodeListResp.setDuration();
        indexNodeListResp.setStartTime();
        return indexNodeListResp;
    }
}
