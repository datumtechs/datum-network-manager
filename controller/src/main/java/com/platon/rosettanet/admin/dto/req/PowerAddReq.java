package com.platon.rosettanet.admin.dto.req;


import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author houzhuang
 * 新增计算节点请求参数
 */
@Data
public class PowerAddReq {

    /** 计算节点名称 */
    @NotNull(message = "计算节点名称不能为空")
    private String powerNodeName;

    /** 节点内网IP */
    @NotNull(message = "内部IP不能为空")
    private String internalIp;

    /** 节点外网IP */
    @NotNull(message = "外部IP不能为空")
    private String externalIp;

    /** 节点内网端口 */
    @NotNull(message = "内部端口不能为空")
    private Integer  internalPort;

    /** 节点外网端口 */
    @NotNull(message = "外部端口不能为空")
    private Integer externalPort;

    /** 节点备注 */
    private String remarks;


}
