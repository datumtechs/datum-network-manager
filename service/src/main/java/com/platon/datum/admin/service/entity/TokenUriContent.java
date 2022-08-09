package com.platon.datum.admin.service.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author liushuyu
 * @date 2022/8/9 15:38
 * @desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class TokenUriContent {

    @ApiModelProperty("凭证名称")
    @NotBlank
    private String name;

    @ApiModelProperty("凭证图片地址")
    @NotBlank
    private String image;

    @ApiModelProperty("凭证描述")
    private String description;
}
