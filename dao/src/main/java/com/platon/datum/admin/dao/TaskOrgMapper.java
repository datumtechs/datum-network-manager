package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.TaskOrg;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface TaskOrgMapper {

    TaskOrg findOrgByIdentityId(@Param("identityId") String identityId);

    int replaceBatch(Collection<TaskOrg> powerProviderList);

    List<TaskOrg> selectAllTaskOrg();

}
