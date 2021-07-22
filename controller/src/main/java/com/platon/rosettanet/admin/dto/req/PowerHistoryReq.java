package com.platon.rosettanet.admin.dto.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "查询节点历史资源请求参数")
public class PowerHistoryReq {

    @NotNull(message = "计算节点ID不能为空")
    @ApiModelProperty(value = "计算节点ID", example = "", required = true)
    private String powerNodeId;

    @NotNull(message = "资源类型不能为空")
    @ApiModelProperty(value = "资源类型（1:cpu, 2:memory, 3:bandwidth）", example = "", required = true)
    private String resourceType;

    @NotNull(message = "时间类型不能为空")
    @ApiModelProperty(value = "时间类型4种（1、7、15、30）", example = "", required = true)
    private String timeType;


}
