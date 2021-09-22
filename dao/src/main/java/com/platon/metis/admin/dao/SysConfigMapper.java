package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.SysConfig;

public interface SysConfigMapper {
    int insert(SysConfig record);

    int insertSelective(SysConfig record);
}