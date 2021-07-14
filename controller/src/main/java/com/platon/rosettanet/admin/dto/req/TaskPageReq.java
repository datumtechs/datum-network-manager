package com.platon.rosettanet.admin.dto.req;


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
public class TaskPageReq {

    @NotNull(message = "页码号不能为空")
    int pageNumber;

    @NotNull(message = "每页数据条数不能为空")
    int pageSize;

    /**
     * 任务状态
     */
    String status;

    /**
     * 任务角色
     */
    Integer role;

    /**
     * 发起任务开始时间戳
     */
    Long startTimestamp;

    /**
     * 发起任务结束时间戳
     */
    Long endTimestamp;

    /**
     * 关键字
     */
    String keyWord;





}
