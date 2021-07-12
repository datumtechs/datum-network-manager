package com.platon.rosettanet.admin.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 * 查询计算节点参数任务列表
 */
@Data
public class PowerJoinTaskReq {

    /** 计算节点ID */
    @NotNull(message = "计算节点ID不能为空")
    private String powerNodeId;

    /** 每页数据条数 */
    private int pageSize;

    /** 起始页号 */
    private int pageNumber;
}
