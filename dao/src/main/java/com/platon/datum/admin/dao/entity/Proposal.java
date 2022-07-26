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
public class Proposal extends BaseDomain {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("委员会事务id")
    private Integer authorityBusinessId;

    /**
     * 提案发起的组织
     */
    @ApiModelProperty("提案发起的组织")
    private String sponsor;

    /**
     * 提案发起的时间
     */
    @ApiModelProperty("提案发起的时间")
    private LocalDateTime startTime;

    /**
     * 提案中被提名的人
     */
    @ApiModelProperty("提案中被提名的人")
    private String nominee;

    /**
     * 提案状态：1-投票未开始（冷静期），2-投票进行中，3-提案已中止 4-提案未通过，5-提案通过
     */
    @ApiModelProperty("提案状态：1-投票未开始（冷静期），2-投票进行中，3-提案已中止 4-提案未通过，5-提案通过")
    private Integer status;

    /**
     * 提案的投票进度
     */
    @ApiModelProperty("提案的投票进度")
    private String progress;

    /**
     * 提案截止时间
     */
    @ApiModelProperty("提案截止时间")
    private String deadline;

    /**
     * 提案附言
     */
    @ApiModelProperty("提案附言")
    private String remark;

    /**
     * 提案的材料
     */
    @ApiModelProperty("提案的材料")
    private String material;

    /**
     * 提案材料的描述
     */
    @ApiModelProperty("提案材料的描述")
    private String materialDesc;
}