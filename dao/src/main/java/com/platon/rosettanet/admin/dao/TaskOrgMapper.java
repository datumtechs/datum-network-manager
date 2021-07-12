package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TaskOrg;
import org.apache.ibatis.annotations.Param;

public interface TaskOrgMapper {

    TaskOrg selectTaskOrgByIdentityId(@Param("identityId") String identityId);
}
