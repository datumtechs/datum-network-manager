package com.platon.datum.admin.dao.enums;

/**
 * @Author liushuyu
 * @Date 2021/7/14 14:50
 * @Version
 * @Desc
 */

import lombok.Getter;

/**
 * 调度服务状态，入网状态
 */

@Getter
public enum CarrierStatusEnum {

    ACTIVE("active","活跃"),
    LEAVE("leave","离开网络"),
    JOIN("join","加入网络"),
    UNUSEFUL("unuseful","不可用"),;

    CarrierStatusEnum(String status,String desc){
        this.status = status;
        this.desc = desc;
    }

    private String status;
    private String desc;
}
