package com.platon.datum.admin.dao.enums;

import lombok.Getter;

/**
 * @Author liushuyu
 * @Date 2021/7/9 18:07
 * @Version
 * @Desc
 */

/**
 * 本地数据文件状态：
 * 数据的状态 (entered：录入数据(创建未发布新表之前的操作); created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)
 */

@Getter
public enum DataFileStatusEnum {
    //(0-未知;1-还未发布的新表;2-已发布的表;3-已撤销的表;101-已删除;102-发布中;103-撤回中;)
    ENTERED(0, "录入数据(创建未发布新表之前的操作)"),
    CREATED(1, "还未发布的新表"),
    RELEASED(2, "已发布的表"),
    REVOKED(3, "已撤销的表"),
    DELETED(101, "已删除"),
    RELEASING(102, "发布中"),
    REVOKING(103, "撤销中"),
    ;

    DataFileStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private int status;
    private String desc;
}
