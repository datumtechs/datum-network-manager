package com.platon.datum.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author juzix
 * @Date 2022/7/21 14:07
 * @Version
 * @Desc ******************************
 */
@Getter
@Setter
@ToString
@ApiModel
public class GlobalOrg {
    /**
     * 身份认证标识的id
     */
    @ApiModelProperty("身份认证标识的id")
    private String identityId;

    /**
     * 身份认证标识的类型 0-未知, 1-DID
     */
    @ApiModelProperty("身份认证标识的类型 0-未知, 1-DID")
    private Integer identityType;

    /**
     * 组织节点ID
     */
    @ApiModelProperty("组织节点ID")
    private String nodeId;

    /**
     * 组织身份名称
     */
    @ApiModelProperty("组织身份名称")
    private String nodeName;

    /**
     * 预留
     */
    @ApiModelProperty("预留")
    private String dataId;

    /**
     * 1 - valid, 2 - invalid
     */
    @ApiModelProperty("组织状态：1 - valid, 2 - invalid")
    private Integer status;

    /**
     * 组织机构图像url
     */
    @ApiModelProperty("组织机构图像url")
    private String imageUrl;

    /**
     * 组织机构简介
     */
    @ApiModelProperty("组织机构简介")
    private String details;

    /**
     * 组织的credentials
     */
    @ApiModelProperty("组织机构简介")
    private String credential;


    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime recUpdateTime;
}