package com.platon.datum.admin.dao.entity;

import cn.hutool.json.JSONUtil;
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
     * 证书模板pctId
     */
    @ApiModelProperty("证书模板pctId")
    private Integer pctId;

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

    /**
     * vc json内容
     */
    @ApiModelProperty("vc json内容")
    private String vc;

    /**
     * 过期时间
     */
    @ApiModelProperty("过期时间")
    private String expirationDate;

    /**
     * 证书状态：0-无效，1-有效
     */
    @ApiModelProperty("证书状态：0-无效，1-有效，2-待生效")
    private Integer status;

    /**
     * context
     */
    @ApiModelProperty("context")
    private String context;

    /**
     * claim
     */
    @ApiModelProperty("claim")
    private String claim;

    public static void main(String[] args) {
        ApplyRecord applyRecord = new ApplyRecord();
        applyRecord.setApplyOrg("申请证书的组织");
        applyRecord.setApproveOrg("审批的组织");
        applyRecord.setStartTime(LocalDateTime.now());//申请时间
        applyRecord.setEndTime(LocalDateTime.now());//审批时间
        applyRecord.setApplyRemark("申请的备注");
        applyRecord.setApproveRemark("审批的备注");
        applyRecord.setMaterial("申请材料的ipfs url");
        applyRecord.setMaterialDesc("审批材料的描述");
        System.out.println(JSONUtil.toJsonStr(applyRecord));
    }
}