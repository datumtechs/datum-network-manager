package com.platon.rosettanet.admin.common.util;

/**
 * @Author liushuyu
 * @Date 2021/7/12 17:06
 * @Version
 * @Desc
 */

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 批量处理数据工具类
 */
public class BatchExecuteUtil {


    /**
     * 批量处理数据
     * @param batchSize 每次处理的数据量
     * @param list 需要处理的列表
     * @param process 处理数据的具体流程
     * @param <T>
     */
    public static <T> void batchExecute(int batchSize, List<T> list, Consumer<List<T>> process){
        int size = list.size();
        int batchNum = size/batchSize;//分几次
        if(size % batchSize > 0){//除不尽则batchNum+1
            batchNum++;
        }
        for (int i = 1; i <= batchNum; i++) {
            int startIndex = batchSize*(i-1);
            int endIndex = batchSize*i;
            if(endIndex > size){
                endIndex = size;
            }
            List<T> tempList = list.subList(startIndex,endIndex);
            process.accept(tempList);
        }
    }

    public static void main(String[] args) {
        AtomicInteger a = new AtomicInteger(0);
        List<Integer> list = Stream.generate(() -> a.incrementAndGet()).limit(2555).collect(Collectors.toList());
        batchExecute(1000,list,(tempList)->{
            System.out.println(tempList);
        });
    }
}
