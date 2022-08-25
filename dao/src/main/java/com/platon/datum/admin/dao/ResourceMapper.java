package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMapper {

    List<Resource> selectByRoleId(@Param("roleId") Integer roleId);

    List<Resource> selectByResourceIdList(@Param("resourceIdList") List<Integer> resourceIdList);
}