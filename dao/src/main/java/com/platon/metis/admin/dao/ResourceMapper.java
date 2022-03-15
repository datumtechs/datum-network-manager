package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.Resource;

import java.util.List;

public interface ResourceMapper {

    List<Resource> selectByRoleId(Integer roleId);
}