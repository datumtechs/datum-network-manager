package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbOrganization;

public interface TbOrganizationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbOrganization record);

    int insertSelective(TbOrganization record);

    TbOrganization selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbOrganization record);

    int updateByPrimaryKey(TbOrganization record);
}