package com.platon.metis.admin.dto.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 任务分页查询Req
 */
@Setter
@Getter
@ToString
@ApiModel(value = "计算任务列表请求参数")
public class TaskPageReq {

    @NotNull(message = "页码号不能为空")
    @ApiModelProperty(value = "起始页号，从1开始", example = "1", required = true)
    int pageNumber;

    @NotNull(message = "每页数据条数不能为空")
    @ApiModelProperty(value = "每页数据条数", example = "10", required = true)
    int pageSize;

    /**
     * 发起任务开始时间戳
     */
    @NotNull(message = "开始时间不能为空，没有就传0")
    @ApiModelProperty(value = "发起任务开始时间戳，时区为UTC+8，没有就传0", example = "1623148140000", required = false)
    Long startTime;

    /**
     * 发起任务结束时间戳
     */
    @NotNull(message = "结束时间不能为空，没有就传0")
    @ApiModelProperty(value = "发起任务结束时间戳，时区为UTC+8，没有就传0", example = "1624876140000", required = false)
    Long endTime;

    @ApiModelProperty(value = "任务状态</br>(0:所有状态</br>1:pending等在中</br>2:running计算中</br>3:failed失败</br>4:success成功)", required = false)
    Integer status;

    @ApiModelProperty(value = "在任务中角色</br>(0:所有角色</br>1:创建者</br>2:算力提供者</br>3:数据提供者</br>4:结果接收者</br>5:算法提供者)", required = false)
    Integer role;
}
