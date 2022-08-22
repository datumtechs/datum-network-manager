package com.platon.datum.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author liushuyu
 * @date 2022/8/22 20:43
 * @desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class OverviewDataTokenOverviewResp {

    @ApiModelProperty("无属性数据凭证数量")
    private Long dataTokenCount = 0L;


    @ApiModelProperty("有属性数据凭证数量")
    private Long attributeDataTokenCount = 0L;
}
