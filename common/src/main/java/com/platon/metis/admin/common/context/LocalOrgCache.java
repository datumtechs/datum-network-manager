package com.platon.metis.admin.common.context;

/**
 * @Author liushuyu
 * @Date 2021/7/12 1:02
 * @Version
 * @Desc 本组织机构身份信息
 */
public class LocalOrgCache {

    public static final String LOCAL_ORG = "local_org";

    public static Object getLocalOrgInfo(){
        return  AppContext.get(LOCAL_ORG);
    }


    /**
     * 在组织身份机构信息插入数据库的时候设置该信息
     * @param localOrgInfo
     */
    public static void setLocalOrgInfo(Object localOrgInfo){
        AppContext.put(LOCAL_ORG,localOrgInfo,Object.class);
    }
}
