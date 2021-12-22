package com.platon.metis.admin.dao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lyf
 * @Description 数据节点修改请求类
 * @date 2021/7/9 15:07
 */
@Data
@ApiModel(value = "资源占用概况响应参数")
public class UsedResourceDTO {

    @ApiModelProperty(value = "使用核数")
    private Integer usedCore;

    @ApiModelProperty(value = "使用内存")
    private Long usedMemory;

    @ApiModelProperty(value = "使用带宽")
    private Long usedBandwidth;

    @ApiModelProperty(value = "总内存")
    private Long totalMemory;

    @ApiModelProperty(value = "总核数")
    private Integer totalCore;

    @ApiModelProperty(value = "总带宽")
    private Long totalBandwidth;

}
