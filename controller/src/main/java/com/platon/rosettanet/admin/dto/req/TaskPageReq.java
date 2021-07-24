package com.platon.rosettanet.admin.dto.req;


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
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态,没有选择传入空字符串,pending: 等在中; failed: 失败; success: 成功", example = "success" , required = false)
    String status;

    /**
     * 任务角色
     */
    @ApiModelProperty(value = "任务角色,我在任务中的角色, 没有就传0 (0: 未定义; 1: 发起方; 2: 数据提供方; 3: 计算参与方; 4: 结果提供方)", example = "0", required = false)
    Integer role;

    /**
     * 发起任务开始时间戳
     */
    @NotNull(message = "开始时间不能为空，没有就传0")
    @ApiModelProperty(value = "发起任务开始时间戳，没有就传0", example = "1623148140000", required = false)
    Long startTime;

    /**
     * 发起任务结束时间戳
     */
    @NotNull(message = "结束时间不能为空，没有就传0")
    @ApiModelProperty(value = "发起任务结束时间戳，没有就传0", example = "1624876140000", required = false)
    Long endTime;

    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字搜索，没有传入空字符串，依据名称搜索", example = "", required = false)
    String keyWord;





}
