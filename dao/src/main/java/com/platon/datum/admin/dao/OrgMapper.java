package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.Org;
import org.apache.ibatis.annotations.Param;

public interface OrgMapper {

    /**
     * 选择性插入字段
     *
     * @param record
     * @return
     */
    int insert(Org record);

    /**
     * 获取可用的调度服务信息
     *
     * @return
     */
    Org selectAvailableCarrier();

    /**
     * 获取组织信息
     *
     * @return
     */
    Org select();

    /**
     * 查询组织身份标识
     *
     * @return
     */
    String selectIdentityId();

    /**
     * 更新
     *
     * @param org
     * @return
     */
    int update(Org org);

    /**
     * 更新组织的委员会成员标志
     *
     * @param isAuthority
     * @return
     */
    int updateIsAuthority(@Param("isAuthority") Integer isAuthority);
}