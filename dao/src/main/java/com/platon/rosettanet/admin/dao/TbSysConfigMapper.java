package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbSysConfig;

public interface TbSysConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbSysConfig record);

    int insertSelective(TbSysConfig record);

    TbSysConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbSysConfig record);

    int updateByPrimaryKey(TbSysConfig record);
}