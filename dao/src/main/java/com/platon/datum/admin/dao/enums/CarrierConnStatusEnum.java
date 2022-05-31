package com.platon.datum.admin.dao.enums;

/**
 * @Author liushuyu
 * @Date 2021/7/14 12:07
 * @Version
 * @Desc
 */

import lombok.Getter;

/**
 * 调度服务连接状态
 */

@Getter
public enum CarrierConnStatusEnum {

    ENABLED("enabled","可用"),
    DISABLED("disabled","不可用"),;

    CarrierConnStatusEnum(String status,String desc){
        this.status = status;
        this.desc = desc;
    }

    private String status;
    private String desc;
}
