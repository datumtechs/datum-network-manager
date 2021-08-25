package com.platon.rosettanet.admin.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author houz
 */
public class ConvertUtil {

    /**
     * list形式响应参数转换
     * @param list1
     * @param o2
     * @param <T>
     * @return
     */
    public static <T> List<T> ConvertToRespList(List<T> list1, Object o2){
        List list2 = new ArrayList();
        synchronized (list2) {
            list1.stream().forEach(o1 -> {
                BeanUtils.copyProperties(o1, o2);
                list2.add(o2);
            });
        }
        return list2;
    }
}
