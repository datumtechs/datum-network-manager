package com.platon.metis.admin.common.context;

import com.platon.metis.admin.common.exception.IdentityIdMissing;

/**
 * @Author liushuyu
 * @Date 2021/7/10 9:55
 * @Version
 * @Desc 本组织机构身份标识
 */
public class LocalOrgIdentityCache {

    public static final String IDENTITY_ID = "identity_id";

    public static String getIdentityId(){
        String identityId = (String)AppContext.get(IDENTITY_ID);
        if(identityId == null){
            throw new IdentityIdMissing();
        }
        return identityId;
    }


    /**
     * 在组织身份机构信息插入数据库的时候设置该信息
     * @param identityId
     */
    public static void setIdentityId(String identityId){
        AppContext.put(IDENTITY_ID,identityId,String.class);
    }
}
