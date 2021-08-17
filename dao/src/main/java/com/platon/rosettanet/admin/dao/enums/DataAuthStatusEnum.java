package com.platon.rosettanet.admin.dao.enums;

/**
 * 数据授权
 */


import lombok.Getter;

/**
 * 授权数据状态：1:同意， 2:拒绝 ，3:待授权
 */

@Getter
public enum DataAuthStatusEnum {

    AGREE(1,"同意"),
    REFUSE(2,"拒绝"),
    PENDING(3,"待授权");

    DataAuthStatusEnum(int status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public int status;
    public String desc;
}
