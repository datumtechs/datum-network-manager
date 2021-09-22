package com.platon.metis.admin.dao.enums;

/**
 * @Author liushuyu
 * @Date 2021/7/13 13:30
 * @Version
 * @Desc
 */

import lombok.Getter;

/**
 * 任任务状态(0:unknown未知、1:pending等在中、2:running计算中、3:failed失败、4:success成功)
 */

@Getter
public enum TaskStatusEnum {

    UNKNOWN(0,"unknown","未知"),
    PENDING(1,"pending","等待中"),
    RUNNING(2,"running","计算中"),
    FAILED(3,"failed","失败"),
    SUCCESS(4,"success","成功");

    TaskStatusEnum(Integer code, String status, String desc){
        this.value = code;
        this.status = status;
        this.desc = desc;
    }

    private Integer value;
    private String status;
    private String desc;
}
