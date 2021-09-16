package com.platon.metis.admin.dao.enums;

import lombok.Getter;

/**
 * @Author liushuyu
 * @Date 2021/7/26 14:46
 * @Version
 * @Desc
 */

@Getter
public enum LocalOrgStatusEnum {

    LEAVE(0,"未入网"),JOIN(1,"已入网"),;

    LocalOrgStatusEnum(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }

    private Integer status;
    private String desc;
}
