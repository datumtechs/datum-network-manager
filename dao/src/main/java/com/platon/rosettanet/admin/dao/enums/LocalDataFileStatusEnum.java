package com.platon.rosettanet.admin.dao.enums;

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

    ENTERED("entered","录入数据(创建未发布新表之前的操作)"),
    CREATED("created","还未发布的新表"),
    RELEASED("released","已发布的表"),
    REVOKED("revoked","已撤销的表"),
    DELETED("deleted","已删除的表");

    LocalDataFileStatusEnum(String status,String desc){
        this.status = status;
        this.desc = desc;
    }

    private String status;
    private String desc;
}
