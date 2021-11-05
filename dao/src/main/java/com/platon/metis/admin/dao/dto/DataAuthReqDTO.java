package com.platon.metis.admin.dao.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author houz
 */
@Data
@ApiModel(value = "数据授权申请")
public class DataAuthReqDTO {

    @ApiModelProperty(value = "申请时间")
    private Date applyTime;

    @ApiModelProperty(value = "申请的组织名称")
    private String orgName;

    @ApiModelProperty(value = "元数据名称")
    private String metaDataName;

}
