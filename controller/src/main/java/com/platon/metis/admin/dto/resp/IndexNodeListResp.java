package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.grpc.common.CommonBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZoneOffset;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:59
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class IndexNodeListResp {

    @ApiModelProperty(name = "jobNodeId", value = "计算节点ID")
    private String jobNodeId;//计算节点ID
    @ApiModelProperty(name = "jobNodeName", value = "计算节点名称")
    private String jobNodeName;//计算节点名称
    @ApiModelProperty(name = "status", value = "计算节点状态：1计算中、0空闲中")
    private String status;//计算节点状态：计算中、空闲中
    @ApiModelProperty(name = "totalProcessor", value = "计算节点提供的CPU总量")
    private Integer totalProcessor;//计算节点提供的CPU总量
    @ApiModelProperty(name = "usedProcessor", value = "计算节点已使用的CPU总量")
    private Integer usedProcessor;//计算节点已使用的CPU总量
    @ApiModelProperty(name = "totalMem", value = "计算节点提供的内存总量，单位：byte")
    private Long totalMem;//计算节点提供的内存总量，单位：byte
    @ApiModelProperty(name = "usedMem", value = "计算节点已使用的内存总量，单位：byte")
    private Long usedMem;//计算节点已使用的内存总量，单位：byte
    @ApiModelProperty(name = "totalBandwidth", value = "计算节点提供的带宽总量，单位：byte")
    private Long totalBandwidth;//计算节点提供的带宽总量，单位：byte
    @ApiModelProperty(name = "usedBandwidth", value = "计算节点已使用的贷款总量，单位：byte")
    private Long usedBandwidth;//计算节点已使用的贷款总量，单位：byte
    @ApiModelProperty(name = "startTime", value = "计算节点的启动时间，单位：秒级时间戳")
    private Long startTime;//计算节点的启动时间，单位：秒级时间戳

    public static IndexNodeListResp from(LocalPowerNode localPowerNode) {
        if(localPowerNode == null){
            return null;
        }
        IndexNodeListResp resp = new IndexNodeListResp();
        resp.setJobNodeId(localPowerNode.getNodeId());
        resp.setJobNodeName(localPowerNode.getNodeName());
        //前端显示计算节点状态：1计算中、0空闲中
        if(localPowerNode.getPowerStatus() == CommonBase.PowerState.PowerState_Occupation_VALUE){//占用
            resp.setStatus("1");
        } else {
            resp.setStatus("0");
        }
        resp.setTotalProcessor(localPowerNode.getCore());
        resp.setUsedProcessor(localPowerNode.getUsedCore());
        resp.setTotalMem(localPowerNode.getMemory());
        resp.setUsedMem(localPowerNode.getUsedMemory());
        resp.setTotalBandwidth(localPowerNode.getBandwidth());
        resp.setUsedBandwidth(localPowerNode.getUsedBandwidth());
        //TODO 时区问题
        resp.setStartTime(localPowerNode.getStartTime() == null? null : localPowerNode.getStartTime().toEpochSecond(ZoneOffset.of("+8")));
        return resp;
    }
}
