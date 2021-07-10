package com.platon.rosettanet.admin.dto.req;

import javax.validation.constraints.NotBlank;

/**
 * @author lyf
 * @Description 数据节点修改请求类
 * @date 2021/7/9 15:07
 */
public class DataNodeUpdateReq {
    @NotBlank(message = "节点id不能为空")
    private String nodeId;

    private String internalIp;

    private Integer internalPort;

    private String externalIp;

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
