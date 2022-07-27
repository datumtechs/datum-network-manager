package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author juzix
 * @Date 2022/7/21 12:02
 * @Version
 * @Desc ******************************
 */
@Getter
@Setter
@ToString
@ApiModel
public class ApplyRecord extends BaseDomain {

    @ApiModelProperty("id")
    private Integer id;

    /**
     * 事务ID
     */
    @ApiModelProperty("委员会事务ID")
    private Integer authorityBusinessId;

    /**
     * 证书类型：1-可信任证书
     */
    @ApiModelProperty("证书类型：1-可信任证书")
    private Byte certificateType;

    /**
     * 发起申请的组织
     */
    @ApiModelProperty("发起申请的组织")
    private String applyOrg;

    /**
     * 审批的组织
     */
    @ApiModelProperty("审批的组织")
    private String approveOrg;

    /**
     * 申请时间
     */
    @ApiModelProperty("申请时间")
    private LocalDateTime startTime;

    /**
     * 审批时间
     */
    @ApiModelProperty("审批时间")
    private LocalDateTime endTime;

    /**
     * 申请进度：0-申请中，1-申请通过，2-申请失败
     */
    @ApiModelProperty("申请进度：0-申请中，1-申请通过，2-申请失败")
    private Integer progress;

    /**
     * 申请备注
     */
    @ApiModelProperty("申请备注")
    private String applyRemark;

    /**
     * 审批备注
     */
    @ApiModelProperty("审批备注")
    private String approveRemark;

    /**
     * 申请材料的url
     */
    @ApiModelProperty("申请材料的url")
    private String material;

    /**
     * 申请材料的描述
     */
    @ApiModelProperty("申请材料的描述")
    private String materialDesc;
}