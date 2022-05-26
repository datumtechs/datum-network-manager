package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 * 查询计算节点参与任务列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "查询计算节点参与任务列表请求参数")
public class PowerJoinTaskReq {

    @NotNull(message = "计算节点ID不能为空")
    @ApiModelProperty(value = "计算节点ID", example = "", required = true)
    private String powerNodeId;

    @NotNull(message = "起始页号不能为空")
    @ApiModelProperty(value = "起始页号", example = "", required = true)
    private int pageNumber;

    @NotNull(message = "每页数据条数不能为空")
    @ApiModelProperty(value = "每页数据条数", example = "", required = true)
    private int pageSize;
}
