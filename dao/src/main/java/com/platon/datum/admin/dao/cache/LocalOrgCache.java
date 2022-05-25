package com.platon.datum.admin.dao.cache;

import com.platon.datum.admin.common.exception.IdentityIdMissing;
import com.platon.datum.admin.common.exception.OrgInfoNotFound;
import com.platon.datum.admin.dao.entity.LocalOrg;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author liushuyu
 * @Date 2021/7/12 1:02
 * @Version
 * @Desc 本组织机构身份信息
 */
public class LocalOrgCache {

    public static final String LOCAL_ORG = "local_org";

    public static LocalOrg getLocalOrgInfo(){
        LocalOrg localOrg = (LocalOrg)AppContext.get(LOCAL_ORG);
        if(localOrg==null){
            throw new OrgInfoNotFound();
        }
        return localOrg;
    }

    public static String getLocalOrgIdentityId(){
        LocalOrg localOrg = (LocalOrg)AppContext.get(LOCAL_ORG);
        if(localOrg==null){
            throw new OrgInfoNotFound();
        }else if(StringUtils.isBlank(localOrg.getIdentityId())){
            throw new IdentityIdMissing();
        }
        return localOrg.getIdentityId();
    }
    /**
     * 在组织身份机构信息插入数据库的时候设置该信息
     * @param localOrgInfo
     */
    public static void setLocalOrgInfo(Object localOrgInfo){
        AppContext.put(LOCAL_ORG,localOrgInfo,Object.class);
    }
}
