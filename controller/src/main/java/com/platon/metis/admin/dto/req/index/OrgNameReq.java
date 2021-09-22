package com.platon.metis.admin.dto.req.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author houz
 * 修改机构名称请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "修改机构名称请求参数")
public class OrgNameReq {

    @ApiModelProperty(value = "机构ID", example = "", required = true)
    @NotBlank(message = "机构ID不能为空")
    private String identityId;

    @ApiModelProperty(value = "机构名称", example = "", required = true)
    @NotBlank(message = "机构名称不能为空")
    private String identityName;




}
