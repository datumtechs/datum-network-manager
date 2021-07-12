package com.platon.rosettanet.admin.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author houzhuang
 * 查询计算节点详情请求参数
 */
@Data
public class PowerSwitchReq {

    /** 计算节点ID */
    @NotNull(message = "计算节点ID不能为空")
    private String powerNodeId;

    /** 计算节点状态 */
    @NotNull(message = "计算节点状态不能为空")
    private String status;

}
