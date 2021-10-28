package com.platon.metis.admin.common.context;

/**
 * @Author liushuyu
 * @Date 2021/7/9 18:44
 * @Version
 * @Desc
 */


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 应用全局缓存
 */
public class AppContext {

    private static final Map<String,CacheValue> CACHE = new ConcurrentHashMap();


    /**
     * 存Cache
     * @param key cache key
     *
     * @param value cache value描述对象
     */
    public static void put(String key,CacheValue value){
        CACHE.put(key,value);
    }

    public static CacheValue getCacheValue(String key){
        CacheValue cacheValue = CACHE.get(key);
        return cacheValue;
    }


    /**
     * 存Cache
     * @param key cache key
     * @param valueType value类型
     * @param value cache value
     */
    public static <T> void put(String key, T value,Class<T> valueType){
        CacheValue<T> cacheValue = new CacheValue();
        cacheValue.setType(valueType);
        cacheValue.setRealValue(value);
        CACHE.put(key,cacheValue);
    }

    /**
     * 取Cache
     * @param key
     * @return
     */
    public static Object get(String key){
        CacheValue cacheValue = CACHE.get(key);
        return cacheValue.getRealValue();
    }


    public static void main(String[] args) {
        //AppContext.put("a",1,Integer.class);
        //System.out.println(AppContext.get("a"));
    }


    /**
     * cache value描述类
     * @param <T>
     */
    @Getter
    @Setter
    @ToString
    public static class CacheValue<T>{
        private Class<T> type;
        private T realValue;
    }
}
