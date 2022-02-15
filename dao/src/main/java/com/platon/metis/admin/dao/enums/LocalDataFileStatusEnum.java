package com.platon.metis.admin.dao.enums;

import lombok.Getter;

/**
 * @Author liushuyu
 * @Date 2021/7/9 18:07
 * @Version
 * @Desc
 */

/**
 * 本地数据文件状态：数据的状态 (entered：录入数据(创建未发布新表之前的操作); created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)
 */

@Getter
public enum LocalDataFileStatusEnum {
    //(0: 未知; 1: 还未发布的新表; 2: 已发布的表; 3: 已撤销的表)
    ENTERED(0,"录入数据(创建未发布新表之前的操作)"),
    CREATED(1,"还未发布的新表"),
    RELEASED(2,"已发布的表"),
    REVOKED(3,"已撤销的表"),
    RELEASING(5,"发布中"),
    REVOKING(6,"撤销中");

    LocalDataFileStatusEnum(int status,String desc){
        this.status = status;
        this.desc = desc;
    }

    private int status;
    private String desc;
}
