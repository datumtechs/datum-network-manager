package com.platon.rosettanet.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author lyf
 * @Description 节点名称校验请求类
 * @date 2021/7/9 11:09
 */
@ApiModel(value = "节点名称校验请求类")
public class CheckDataNodeNameReq {
    /**
     * 节点名称
     */
    @ApiModelProperty(name = "nodeName", value = "节点名称", required = true)
    private String nodeName;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
