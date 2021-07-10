package com.platon.rosettanet.admin.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author houzhuang
 * 查询计算节点详情请求参数
 */
@Data
public class PowerListSelectReq {

    /** 组织机构ID */
    @NotNull(message = "组织机构ID不能为空")
    private String identityId;

}
