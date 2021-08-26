package com.platon.rosettanet.admin.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author houz
 */
public class ConvertUtil {

    /**
     * 列表对象非并行转换工具
     * @param list1
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> convertSerialToList(List<T> list1, Class<T> tClass) {
        List<T> list2 = new ArrayList();
        synchronized (list2) {
            list1.stream().forEach(o1 -> {
                T target = ReflectUtil.newInstanceIfPossible(tClass);
                BeanUtil.copyProperties(o1, target);
                list2.add(target);
            });
        }
        return list2;
    }

    /**
     * 列表对象并行转换工具(性能好，会乱序)
     * @param list1
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> convertParallelToList(List<T> list1, Class<T> tClass) {
        List<T> list2 = new ArrayList();
        synchronized (list2) {
            list1.parallelStream().forEach(o1 -> {
                T target = ReflectUtil.newInstanceIfPossible(tClass);
                BeanUtil.copyProperties(o1, target);
                list2.add(target);
            });
        }
        return list2;
    }
}
