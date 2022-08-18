package com.platon.datum.admin.dto.req;

import com.platon.datum.admin.dto.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author houz
 * 查询计算节点参与任务列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "查询计算节点参与任务列表请求参数")
public class PowerJoinTaskReq extends CommonPageReq {

    @NotBlank(message = "计算节点ID不能为空")
    @ApiModelProperty(value = "计算节点ID", example = "", required = true)
    private String powerNodeId;

}
