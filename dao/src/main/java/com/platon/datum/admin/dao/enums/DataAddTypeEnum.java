package com.platon.datum.admin.dao.enums;

/**
 * 数据新增
 */


import lombok.Getter;

/**
 * 数据新增类型：1:新增数据， 2:另存为新数据
 */

@Getter
public enum DataAddTypeEnum {
    ADD(1,"新增数据"),
    ADD_AGAIN(2,"另存为新数据");

    DataAddTypeEnum(int type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public int type;
    public String desc;
}
