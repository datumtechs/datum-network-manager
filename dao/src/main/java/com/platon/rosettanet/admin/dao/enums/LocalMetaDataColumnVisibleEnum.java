package com.platon.rosettanet.admin.dao.enums;

/**
 * @Author liushuyu
 * @Date 2021/7/10 12:26
 * @Version
 * @Desc
 */

import lombok.Getter;

/**
 * 元数据列是否可见
 */

@Getter
public enum LocalMetaDataColumnVisibleEnum {
    YES("Y","可见"),NO("N","不可见"),;

    LocalMetaDataColumnVisibleEnum(String isVisible,String desc){
        this.isVisible = isVisible;
        this.desc = desc;
    }

    private String isVisible;
    private String desc;
}
