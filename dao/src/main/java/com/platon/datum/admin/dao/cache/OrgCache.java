package com.platon.datum.admin.dao.cache;

import com.platon.datum.admin.common.exception.IdentityIdMissing;
import com.platon.datum.admin.common.exception.OrgInfoNotFound;
import com.platon.datum.admin.dao.entity.Org;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author liushuyu
 * @Date 2021/7/12 1:02
 * @Version
 * @Desc 本组织机构身份信息
 */
public class OrgCache {

    public static final String LOCAL_ORG = "local_org";

    public static Org getLocalOrgInfo() throws OrgInfoNotFound {
        Org org = (Org) AppContext.get(LOCAL_ORG);
        if (org == null) {
            throw new OrgInfoNotFound();
        }
        return org;
    }

    public static String getLocalOrgIdentityId() throws OrgInfoNotFound, IdentityIdMissing {
        Org org = (Org) AppContext.get(LOCAL_ORG);
        if (org == null) {
            throw new OrgInfoNotFound();
        } else if (StringUtils.isBlank(org.getIdentityId())) {
            throw new IdentityIdMissing();
        }
        return org.getIdentityId();
    }

    public static boolean identityIdNotFound() {
        Org org = (Org) AppContext.get(LOCAL_ORG);
        if (org == null || StringUtils.isBlank(org.getIdentityId())) {
            return true;
        }
        return false;
    }

    /**
     * 在组织身份机构信息插入数据库的时候设置该信息
     *
     * @param localOrgInfo
     */
    public static void setLocalOrgInfo(Object localOrgInfo) {
        AppContext.put(LOCAL_ORG, localOrgInfo, Object.class);
    }
}
