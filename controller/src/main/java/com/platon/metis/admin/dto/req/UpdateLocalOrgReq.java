package com.platon.metis.admin.dto.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ApiModel(value = "更新本地组织信息")
public class UpdateLocalOrgReq {
    @ApiModelProperty(value = "组织名称", required = true)
    String name;

    @ApiModelProperty(value = "组织头像URL", required = true)
    String imageUrl;

    @ApiModelProperty(value = "组织描述", required = true)
    String profile;
}