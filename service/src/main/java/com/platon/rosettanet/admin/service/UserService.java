package com.platon.rosettanet.admin.service;

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
    String applyOrgName(String orgName);
}
