package com.platon.metis.admin.dto.resp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * 连接调度返回参数
 */
@Data
@ApiModel(value = "连接调度返回参数")
public class ConnectNodeResp {

    @ApiModelProperty(value = "连接调度服务状态 enabled：可用, disabled:不可用")
    private String carrierConnStatus;


}
