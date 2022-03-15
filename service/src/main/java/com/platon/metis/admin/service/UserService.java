package com.platon.metis.admin.service;

import com.platon.metis.admin.dao.entity.SysUser;

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

    /**
     * 根据地址查询用户
     * @param address
     * @return
     */
    SysUser getByAddress(String address);

    /**
     * 新增用户
     */
    SysUser createUser(String hexAddress);

    /**
     * 根据用户地址更新用户信息
     * @param sysUser
     */
    void updateByAddress(SysUser sysUser);
}
