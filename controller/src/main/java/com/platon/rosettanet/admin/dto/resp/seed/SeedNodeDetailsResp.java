package com.platon.rosettanet.admin.dto.resp.seed;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 种子节点实体类
 */
@Data
public class SeedNodeDetailsResp {

    @ApiModelProperty(value = "序号ID")
    private Integer id;

    @ApiModelProperty(value = "组织身份ID")
    private String identityId;

    @ApiModelProperty(value = "种子节点ID")
    private String seedNodeId;

    @ApiModelProperty(value = "节点名称")
    private String seedNodeName;

    @ApiModelProperty(value = "节点内部IP")
    private String internalIp;

    @ApiModelProperty(value = "节点内部端口")
    private Integer internalPort;

    @ApiModelProperty(value = "节点状态 -1: 网络连接失败; 0: 网络连接成功")
    private Integer connStatus;

    @ApiModelProperty(value = "是否是初始节点(0:否, 1:是)")
    private Integer initFlag;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;

}