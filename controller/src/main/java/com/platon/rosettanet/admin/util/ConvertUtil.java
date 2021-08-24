package com.platon.rosettanet.admin.util;

import org.springframework.beans.BeanUtils;

public class ConvertUtil {


    public static <T> Object ConvertToResp(Object o1, Object o2){
        BeanUtils.copyProperties(o1, o2);
        return o2;
    }
}
