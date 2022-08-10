package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.Org;
import org.apache.ibatis.annotations.Param;

/**
 * @Author juzix
 * @Date 2022/8/9 10:32
 * @Version 
 * @Desc 
 *******************************
 */
public interface OrgMapper {

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
     * 选择性插入字段，无法插入null值进去
     * @param record
     * @return
     */
    int insertSelective(Org record);

    /**
     * 选择性更新，无法更新null值进去
     * @param org
     * @return
     */
    int updateSelective(Org org);

    /**
     * 更新，可更新null值进去
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

    /**
     * 更新组织的credential
     * @param credential
     * @return
     */
    int updateCredential(String credential);
}