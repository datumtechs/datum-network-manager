package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.TaskOrg;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface TaskOrgMapper {

    TaskOrg selectTaskOrgByIdentityId(@Param("identityId") String identityId);

    int insertBatch(Collection<TaskOrg> powerProviderList);

    List<TaskOrg> selectAllTaskOrg();

}
