package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {

    SysUser selectByUserNameAndPwd(@Param("userName") String userName, @Param("password") String password);

}