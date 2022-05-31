package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houzhuang
 * 修改计算节点名称请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "修改计算节点名称请求")
public class PowerUpdateReq {

    @ApiModelProperty(value = "计算节点ID", example = "", required = true)
    private String nodeId;

    @ApiModelProperty(value = "计算节点名称", example = "", required = true)
    private String nodeName;


}
