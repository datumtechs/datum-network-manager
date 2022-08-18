package com.platon.datum.admin.dto.req;

import com.platon.datum.admin.dto.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author houzhuang
 * 查询计算节点服务列表请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "查询计算节点列表请求参数")
public class PowerQueryListReq extends CommonPageReq {

    @NotBlank(message = "组织机构ID不能为空")
    @ApiModelProperty(value = "组织机构ID", example = "", required = true)
    private String identityId;

    @ApiModelProperty(value = "计算节点名称", example = "", required = false)
    private String keyword;

}
