package com.platon.datum.admin.common.util;

import java.util.UUID;

/**
 * @Author liushuyu
 * @Date 2021/7/9 0:01
 * @Version
 * @Desc id 工具类
 */
public class IDUtil {

    public static final String IDENTITY_ID_PREFIX = "identity:";
    public static final String DID_PREFIX = "did:pid:";

    /**
     * 生成 带前缀的id，项目中需要生成ID的地方都可以使用，第一版可以先使用UUID过渡，后期更新ID生成策略
     * @param prefix 前缀
     * @return 返回一个ID
     * @example identity:beff0bf738814a2794cb6fbefe55dc55
     */
    public static String generate(String prefix){
        String id = UUID.randomUUID().toString().replace("-", "");
        if(prefix == null){
            return id;
        }
        return prefix.concat(id);
    }

    public static String generateDid(String address){
        return DID_PREFIX.concat(address);
    }

    public static void main(String[] args) {
        System.out.println(generate(IDENTITY_ID_PREFIX));
    }
}
