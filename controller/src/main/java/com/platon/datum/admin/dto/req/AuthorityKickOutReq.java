package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Author liushuyu
 * @Date 2022/7/22 14:41
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class AuthorityKickOutReq {

    @ApiModelProperty(value = "被提名组织的identityId", required = true)
    @NotBlank
    private String identityId;

    @ApiModelProperty(value = "提名附言", required = true)
    @NotBlank
    private String remark;

    @ApiModelProperty(value = "审批资料url", required = false)
    private String material;

    @ApiModelProperty(value = "审批资料描述", required = false)
    private String materialDesc;
}
