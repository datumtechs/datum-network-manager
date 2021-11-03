package com.platon.metis.admin.dto.req.seed;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author houz
 * 查询种子节点列表请求参数
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "查询种子节点列表请求参数")
public class ListSeedNodeReq {

    @NotNull(message = "每页数据条数不能为空")
    @ApiModelProperty(value = "每页数据条数", example = "", required = true)
    private int pageSize;

    @NotNull(message = "起始页号不能为空")
    @ApiModelProperty(value = "起始页号", example = "", required = true)
    private int pageNumber;


}
