package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {

    SysUser selectByAddress(@Param("address") String address);

    int insert(SysUser sysUser);

    int updateByAddress(SysUser sysUser);

    //如果数据库只剩下一个用户，则将其设置为管理员用户
    int updateSingleUserToAdmin();

    int insertOrUpdateToAdmin(SysUser user);
}