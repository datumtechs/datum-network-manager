package com.platon.datum.admin.service;

import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dao.entity.SysUser;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:25
 * @Version
 * @Desc
 */
public interface UserService {

    /**
     * 申请身份标识
     */
    Org applyOrgIdentity();

    /**
     * 根据地址查询用户
     *
     * @param address
     * @return
     */
    SysUser getByAddress(String address);

    /**
     * 新增用户
     */
    SysUser createUser(String hexAddress);

    /**
     * 更新管理员钱包地址
     *
     * @param user       当前管理员
     * @param newAddress 新管理员钱包地址
     */
    void updateAdmin(SysUser user, String newAddress);


    /**
     * 设置组织名称
     *
     * @param orgName
     * @return
     */
    Org setOrgName(String orgName);
}
