package com.platon.rosettanet.admin.dao.enums;

/**
 * @Author liushuyu
 * @Date 2021/7/13 13:30
 * @Version
 * @Desc
 */

import lombok.Getter;

/**
 * 任务的状态：任务状态 pending: 等待中; running: 计算中; failed: 失败; success: 成功
 */

@Getter
public enum TaskStatusEnum {

    PENDING("pending","等待中"),
    RUNNING("running","计算中"),
    FAILED("failed","失败"),
    SUCCESS("success","成功");

    TaskStatusEnum(String status, String desc){
        this.status = status;
        this.desc = desc;
    }

    private String status;
    private String desc;
}
