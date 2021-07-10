package com.platon.rosettanet.admin.dto.req;

import javax.validation.constraints.NotBlank;

/**
 * @author lyf
 * @Description 数据节点删除请求类
 * @date 2021/7/9 14:53
 */
public class DataNodeDeleteReq {
    @NotBlank(message = "节点id不能为空")
    private String nodeId;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
