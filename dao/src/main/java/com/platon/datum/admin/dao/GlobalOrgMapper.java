package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.GlobalOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author juzix
 * @Date 2022/7/21 14:07
 * @Version
 * @Desc ******************************
 */
public interface GlobalOrgMapper {
    int deleteByIdentityId(String identityId);

    GlobalOrg selectByIdentityId(String identityId);

    int insertOrUpdate(@Param("identityList") List<GlobalOrg> identityList);
}