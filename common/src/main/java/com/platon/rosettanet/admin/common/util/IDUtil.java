package com.platon.rosettanet.admin.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * @Author liushuyu
 * @Date 2021/7/9 0:01
 * @Version
 * @Desc id 工具类
 */
public class IDUtil {

    public static final String IDENTITY_ID_PREFIX = "identity_";

    /**
     * 生成 id，项目中需要生成ID的地方都可以使用，第一版可以先使用UUID过渡，后期更新ID生成策略
     * @return 返回一个32位字符的ID
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
     * @example identity_beff0bf738814a2794cb6fbefe55dc55
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
