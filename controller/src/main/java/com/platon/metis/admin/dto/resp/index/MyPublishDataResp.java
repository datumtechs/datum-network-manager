package com.platon.metis.admin.dto.resp.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 
 * VIEW
 */
@Data
@ApiModel(value = "资源占用概况响应参数")
public class MyPublishDataResp {

    @ApiModelProperty(value = "发布时间")
    private String publishTime;

    @ApiModelProperty(value = "数据大小")
    private Long sizeTotal;

}