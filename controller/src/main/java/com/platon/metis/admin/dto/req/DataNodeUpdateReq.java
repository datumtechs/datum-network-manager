package com.platon.metis.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author lyf
 * @Description 数据节点修改请求类
 * @date 2021/7/9 15:07
 */
@ApiModel(value = "数据节点修改请求类")
public class DataNodeUpdateReq {
    @ApiModelProperty(name = "nodeId", value = "节点id", required = true)
    @NotBlank(message = "节点id不能为空")
    private String nodeId;

    @ApiModelProperty(name = "internalIp", value = "内部ip地址")
    private String internalIp;

    @ApiModelProperty(name = "internalPort", value = "内部端口号")
    private Integer internalPort;

    @ApiModelProperty(name = "externalIp", value = "外部ip地址")
    private String externalIp;

    @ApiModelProperty(name = "externalPort", value = "外部端口号")
    private Integer externalPort;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }

    public Integer getInternalPort() {
        return internalPort;
    }

    public void setInternalPort(Integer internalPort) {
        this.internalPort = internalPort;
    }

    public String getExternalIp() {
        return externalIp;
    }

    public void setExternalIp(String externalIp) {
        this.externalIp = externalIp;
    }

    public Integer getExternalPort() {
        return externalPort;
    }

    public void setExternalPort(Integer externalPort) {
        this.externalPort = externalPort;
    }
}
