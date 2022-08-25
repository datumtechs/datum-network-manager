package com.platon.datum.admin.dao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author liushuyu
 * @date 2022/8/25 12:14
 * @desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class DataTokenOverviewDTO {

    @ApiModelProperty("未定价无属性数据凭证数量")
    private Long pricedDataTokenCount = 0L;

    @ApiModelProperty("已定价无属性数据凭证数量")
    private Long unPriceddataTokenCount = 0L;

    @ApiModelProperty("有属性数据凭证数量")
    private Long attributeDataTokenCount = 0L;

}
