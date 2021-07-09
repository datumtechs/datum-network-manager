package com.platon.rosettanet.admin.common.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @Author liushuyu
 * @Date 2021/7/9 0:01
 * @Version
 * @Desc id 工具类
 */
public class IDUtils {


    /**
     * 生成 id，项目中需要生成ID的地方都可以使用，第一版可以先使用UUID过渡，后期更新ID生成策略
     * @return 返回一个ID
     * @example 71a51fcac1164bdaa6af10286670d11d
     */
    public static String generate(){
        String id = IdUtil.fastSimpleUUID();
        return id;
    }

    /**
     * 生成 带前缀的id，项目中需要生成ID的地方都可以使用，第一版可以先使用UUID过渡，后期更新ID生成策略
     * @param prefix 前缀
     * @return 返回一个ID
     * @example 71a51fcac1164bdaa6af10286670d11d
     */
    public static String generate(String prefix){
        String id = generate();
        if(prefix == null){
            return id;
        }
        return prefix.concat(id);
    }

    public static void main(String[] args) {
        System.out.println(generate());
        System.out.println(generate("identity_"));
    }
}
