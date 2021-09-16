package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {

    SysUser selectByUserNameAndPwd(@Param("userName") String userName, @Param("password") String password);

    /**
     * 修改机构识别名称
     * @param identityName 机构名称
     * @param identityId 机构id
     */
    void updateOrgName(@Param(value = "identityId")String identityId, @Param(value = "identityName")String identityName);


}