package com.platon.rosettanet.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lyf
 * @Description 节点分页查询请求类
 * @date 2021/7/8 17:14
 */
@Data
@ApiModel(value = "节点分页查询请求类")
public class NodePageReq {

    @ApiModelProperty(value = "起始页号", required = true)
    @NotNull(message = "起始页号不能为空")
    private Integer pageNumber;

    @ApiModelProperty(value = "每页数据条数", required = true)
    @NotNull(message = "每页数据条数不能为空")
    private Integer pageSize;

    @ApiModelProperty(value = "搜索关键字(包含ID和名称)", example = "", required = true)
    private String keyword;

}
