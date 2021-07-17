package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {

    SysUser selectByUserNameAndPwd(@Param("userName") String userName, @Param("password") String password);

}