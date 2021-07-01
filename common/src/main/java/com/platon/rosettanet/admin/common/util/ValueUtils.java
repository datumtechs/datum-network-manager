package com.platon.rosettanet.admin.common.util;

public class ValueUtils {
    public static int intValue(Integer i){
        if (i==null){
            return 0;
        }else{
            return i.intValue();
        }
    }

    public static long longValue(Long l){
        if (l==null){
            return 0L;
        }else{
            return l.longValue();
        }
    }
}
