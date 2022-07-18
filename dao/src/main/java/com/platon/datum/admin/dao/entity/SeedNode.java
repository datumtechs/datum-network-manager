package com.platon.datum.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author houz
 * 种子节点实体类
 */
@Data
@ApiModel(value = "种子节点")
public class SeedNode {

    @ApiModelProperty(value = "种子节点ID")
    private String seedNodeId;

    @ApiModelProperty(value = "节点状态 0: 未被调度服务连接上; 1: 连接上;")
    private Integer connStatus;

    @ApiModelProperty(value = "是否是初始节点(false:否, true:是)")
    private Boolean initFlag;
}