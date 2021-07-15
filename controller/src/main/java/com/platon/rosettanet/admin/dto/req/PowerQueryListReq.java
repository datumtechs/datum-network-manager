package com.platon.rosettanet.admin.dto.req;

import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author houzhuang
 * 查询计算节点服务列表请求参数
 */
@Data
@ApiModel(value = "查询计算节点列表请求参数")
public class PowerQueryListReq {

    @NotNull(message = "组织机构ID不能为空")
    @ApiModelProperty(value = "组织机构ID", example = "", required = true)
    private String identityId;

    @ApiModelProperty(value = "计算节点名称", example = "", required = false)
    private String keyword;

    @NotNull(message = "每页数据条数不能为空")
    @ApiModelProperty(value = "每页数据条数", example = "", required = true)
    private int pageSize;

    @NotNull(message = "起始页号不能为空")
    @ApiModelProperty(value = "起始页号", example = "", required = true)
    private int pageNumber;

}
