package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TaskOrg;
import com.platon.rosettanet.admin.dao.entity.TaskPowerProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskOrgMapper {

    TaskOrg selectTaskOrgByIdentityId(@Param("identityId") String identityId);

    int insertBatch(List<TaskOrg> powerProviderList);

}
