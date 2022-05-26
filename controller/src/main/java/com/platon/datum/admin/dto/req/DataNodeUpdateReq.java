package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lyf
 * @Description 修改数据节点名称请求
 * @date 2021/7/9 15:07
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "修改数据节点名称请求")
public class DataNodeUpdateReq {
    @ApiModelProperty(name = "nodeId", value = "节点id", required = true)
    private String nodeId;

    @ApiModelProperty(value = "数据节点名称", example = "", required = true)
    private String nodeName;
}
