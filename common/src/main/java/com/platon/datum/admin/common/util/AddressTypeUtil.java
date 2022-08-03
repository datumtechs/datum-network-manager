package com.platon.datum.admin.common.util;

import cn.hutool.core.util.HexUtil;

import java.util.Objects;

/**
 * @Author liushuyu
 * @Date 2022/8/2 18:04
 * @Version
 * @Desc
 */
public class AddressTypeUtil {

    public static Integer getType(String address){
        Objects.requireNonNull(address);
        int type = 1;
        if(HexUtil.isHexNumber(address)){
            type = 1;
        } else if(address.startsWith("lat")){
            type = 3;
        } else {
            type = 2;
        }
        return type;
    }

    public static void main(String[] args) {
        System.out.println(getType("0x25899fB267bd235D7d5681e4cf63592632C8Cf13"));
    }
}
