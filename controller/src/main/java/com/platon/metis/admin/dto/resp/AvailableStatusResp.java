package com.platon.metis.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author lyf
 * @Description 可用状态响应实体类
 * @date 2021/7/9 11:06
 */
@ApiModel("可用状态响应实体类")
public class AvailableStatusResp {
    @ApiModelProperty(name = "status",value = "状态码，N 可用 ，D 不可用")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
