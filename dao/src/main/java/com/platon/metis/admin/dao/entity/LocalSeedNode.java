package com.platon.metis.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 种子节点实体类
 */
@Data
@ApiModel(value = "种子节点")
public class LocalSeedNode {

    @ApiModelProperty(value = "序号ID")
    private Integer id;

    @ApiModelProperty(value = "组织身份ID")
    private String identityId;

    @ApiModelProperty(value = "外部节点ID")
    private String outNodeId;

    /*@ApiModelProperty(value = "种子节点ID")
    private String seedNodeId;*/

    @ApiModelProperty(value = "节点名称")
    private String seedNodeName;

    @ApiModelProperty(value = "种子节点地址")
    private String seedNodeAddress;

    @ApiModelProperty(value = "节点状态 -1: 网络连接失败; 0: 网络连接成功")
    private Integer connStatus;

    @ApiModelProperty(value = "是否是初始节点(0:否, 1:是)")
    private Integer initFlag;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;

}