package com.platon.datum.admin.dao.enums;

/**
 * @Author liushuyu
 * @Date 2021/7/13 13:30
 * @Version
 * @Desc
 */

import lombok.Getter;

/**
 * 系统用户的状态：用户状态 enabled：可用, disabled:不可用
 */

@Getter
public enum SysUserStatusEnum {

    ENABLED("enabled","可用"),DISABLED("disabled","不可用"),;

    SysUserStatusEnum(String status,String desc){
        this.status = status;
        this.desc = desc;
    }

    private String status;
    private String desc;
}
