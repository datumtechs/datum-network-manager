package com.platon.metis.admin.service;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:25
 * @Version
 * @Desc
 */
public interface UserService {

    /**
     * 申请身份标识
     * @param orgName 身份标识名称
     * @return 申请成功后的组织ID
     */
    String applyOrgIdentity(String orgName);

    String login(String userName, String passwd);

    /**
     * 修改机构识别名称
     * @param identityName 机构名称
     * @param identityId 机构id
     */
    void updateOrgName(String identityId, String identityName);

}
