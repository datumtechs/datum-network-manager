package com.platon.metis.admin.dto.resp.index;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author houz
 */
@Data
@ApiModel(value = "待授权列表响应参数")
public class WaitAuthDataResp {

    @ApiModelProperty(value = "申请时间")
    private Date applyTime;

    @ApiModelProperty(value = "申请用户")
    private String applyUser;

    @ApiModelProperty(value = "数据名称")
    private String dataName;

}
