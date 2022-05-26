package com.platon.datum.admin.dao.enums;

public enum DataAuthTypeEnum {
    TIME_PERIOD(1,"时间段"),
    TIMES(2,"次数");
    DataAuthTypeEnum(int type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public int type;
    public String desc;
}
