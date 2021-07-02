package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbSysUser;

public interface TbSysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbSysUser record);

    int insertSelective(TbSysUser record);

    TbSysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbSysUser record);

    int updateByPrimaryKey(TbSysUser record);
}