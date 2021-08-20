package com.platon.rosettanet.admin.enums;

/**
 * 数据授权
 */


import lombok.Getter;

/**
 * 数据授权状态, 1:待授权数据， 2:已授权数据(同意授权 + 拒绝授权)
 */

@Getter
public enum DtoAuthStatusEnum {

    AUTH_UNFINISH(1,"待授权数据"),
    AUTH_FINISH(2,"已授权数据");

    DtoAuthStatusEnum(int status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public int status;
    public String desc;
}
