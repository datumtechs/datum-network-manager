package com.platon.datum.admin.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Date;

import com.platon.datum.admin.dao.entity.Authority;

/**
 * @Author juzix
 * @Date 2022/7/21 12:02
 * @Version
 * @Desc ******************************
 */
public interface AuthorityMapper {
    int deleteByPrimaryKey(String identityId);

    int insert(Authority record);

    Authority selectByPrimaryKey(String identityId);

    int updateByIdentityId(Authority record);

    /**
     * 根据名字模糊查询出所有委员会成员
     *
     * @return
     */
    List<Authority> selectList(@Param("keyword") String keyword);

    /**
     * 查询出委员会成员的总数
     *
     * @return
     */
    int selectCount();

    void refresh(@Param("allAuthority") List<Authority> allAuthority);
}