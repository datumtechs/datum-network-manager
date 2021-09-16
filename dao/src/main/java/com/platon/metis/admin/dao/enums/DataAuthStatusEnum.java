package com.platon.metis.admin.dao.enums;

/**
 * 数据授权
 */


import lombok.Getter;

/**
 * 授权数据状态：0:待授权，1:同意， 2:拒绝
 */

@Getter
public enum DataAuthStatusEnum {
    PENDING(0,"待授权"),
    AGREE(1,"同意"),
    REFUSE(2,"拒绝");

    DataAuthStatusEnum(int status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public int status;
    public String desc;
}
