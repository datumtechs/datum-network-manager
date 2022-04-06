package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMapper {

    List<Resource> selectByRoleId(Integer roleId);

    List<Resource> selectByResourceIdList(@Param("resourceIdList") List<Integer> resourceIdList);
}