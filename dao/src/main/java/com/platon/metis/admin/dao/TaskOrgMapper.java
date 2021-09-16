package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.TaskOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface TaskOrgMapper {

    TaskOrg selectTaskOrgByIdentityId(@Param("identityId") String identityId);

    int insertBatch(Set<TaskOrg> powerProviderList);

    List<TaskOrg> selectAllTaskOrg();

}
