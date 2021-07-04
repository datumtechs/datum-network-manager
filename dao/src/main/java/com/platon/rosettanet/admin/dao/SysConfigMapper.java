package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.SysConfig;

public interface SysConfigMapper {
    int insert(SysConfig record);

    int insertSelective(SysConfig record);
}