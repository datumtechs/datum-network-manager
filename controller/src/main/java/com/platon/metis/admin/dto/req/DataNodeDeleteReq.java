package com.platon.metis.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author lyf
 * @Description 数据节点删除请求类
 * @date 2021/7/9 14:53
 */
@ApiModel(value = "数据节点删除请求类")
public class DataNodeDeleteReq {
    @ApiModelProperty(name = "nodeId", value = "节点id", required = true)
    @NotBlank(message = "节点id不能为空")
    private String nodeId;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
