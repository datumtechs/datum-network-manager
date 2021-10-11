package com.platon.metis.admin.dto.req.auth;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 授权分页查询Req
 */
@Data
@ApiModel(value = "数据授权列表请求参数")
public class AuthPageReq {

    @NotNull(message = "页码号不能为空")
    @ApiModelProperty(value = "起始页号，从1开始", example = "1", required = true)
    int pageNumber;

    @NotNull(message = "每页数据条数不能为空")
    @ApiModelProperty(value = "每页数据条数", example = "10", required = true)
    int pageSize;

    /**
     * 授权状态
     */
    //@NotNull(message = "数据授权状态状态不能为空")
    @ApiModelProperty(value = "数据授权状态,0：未定义， 1:待授权数据， 2:已授权数据(同意授权 + 拒绝授权)", example = "0" , required = true)
    Integer status;


    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字搜索，没有传入空字符串，依据名称或Id搜索", example = "", required = false)
    String keyWord;





}
