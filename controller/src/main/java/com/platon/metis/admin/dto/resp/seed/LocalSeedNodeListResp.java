package com.platon.metis.admin.dto.resp.seed;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author houz
 * 计算节点返回列表
 */
@Data
@ApiModel(value = "种子节点返回列表")
public class LocalSeedNodeListResp {

    @ApiModelProperty(value = "种子节点ID", example = "")
    private String seedNodeId;

    @ApiModelProperty(value = "种子节点名称", example = "")
    private String seedNodeName;

    @ApiModelProperty(value = "节点内网IP", example = "")
    private String internalIp;

    @ApiModelProperty(value = "节点内网端口", example = "")
    private Integer internalPort;

    @ApiModelProperty(value = "节点状态 -1: 网络连接失败; 0: 网络连接成功", example = "")
    private String connStatus;

    @ApiModelProperty(value = "是否是初始节点(0:否, 1:是)", example = "")
    private String initFlag;

}