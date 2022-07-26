package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Author liushuyu
 * @Date 2022/7/21 18:37
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class GeneralOrganizationApplyReq {

    @ApiModelProperty(value = "审批节点", required = true)
    @NotBlank
    private String approve;

    @ApiModelProperty(value = "申请附言", required = true)
    @NotBlank
    private String remark;

    @ApiModelProperty("审批资料URL")
    private String material;

    @ApiModelProperty("审批资料描述:限200个字符")
    private String desc;
}
