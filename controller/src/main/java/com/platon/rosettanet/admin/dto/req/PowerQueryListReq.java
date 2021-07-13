package com.platon.rosettanet.admin.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author houzhuang
 * 查询计算节点详情请求参数
 */
@Data
public class PowerQueryListReq {

    /** 组织机构ID */
    @NotNull(message = "组织机构ID不能为空")
    private String identityId;

    /** 计算节点名称 */
    private String keyword;

    /** 每页数据条数 */
    @NotNull(message = "每页数据条数不能为空")
    private int pageSize;

    /** 起始页号 */
    @NotNull(message = "起始页号不能为空")
    private int pageNumber;

}
