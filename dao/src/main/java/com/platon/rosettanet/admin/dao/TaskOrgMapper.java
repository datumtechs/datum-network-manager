package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TaskOrg;
import com.platon.rosettanet.admin.dao.entity.TaskPowerProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface TaskOrgMapper {

    TaskOrg selectTaskOrgByIdentityId(@Param("identityId") String identityId);

    int insertBatch(Set<TaskOrg> powerProviderList);

    List<TaskOrg> selectAllTaskOrg();

}
