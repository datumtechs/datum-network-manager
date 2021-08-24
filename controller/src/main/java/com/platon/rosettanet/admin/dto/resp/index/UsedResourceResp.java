package com.platon.rosettanet.admin.dto.resp.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 
 * VIEW
 */
@Data
@ApiModel(value = "资源占用概况响应参数")
public class UsedResourceResp {

    @ApiModelProperty(value = "使用核数占比")
    private String usedCore;

    @ApiModelProperty(value = "使用内存占比")
    private String usedMemory;

    @ApiModelProperty(value = "使用带宽占比")
    private String usedBandwidth;

}