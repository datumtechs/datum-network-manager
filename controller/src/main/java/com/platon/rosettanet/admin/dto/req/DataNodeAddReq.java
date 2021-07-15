package com.platon.rosettanet.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author lyf
 * @Description 数据节点新增请求类
 * @date 2021/7/9 10:42
 */
@ApiModel(value = "数据节点新增请求类")
public class DataNodeAddReq {

    @ApiModelProperty(name = "nodeName", value = "节点名称", required = true)
    @NotBlank(message = "节点名称不能为空")
    private String nodeName;

    @ApiModelProperty(name = "internalIp", value = "内部ip地址")
    private String internalIp;

    @ApiModelProperty(name = "internalPort", value = "内部端口号")
    private Integer internalPort;

    @ApiModelProperty(name = "externalIp", value = "外部ip地址")
    private String externalIp;

    @ApiModelProperty(name = "externalPort", value = "外部端口号")
    private Integer externalPort;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
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
