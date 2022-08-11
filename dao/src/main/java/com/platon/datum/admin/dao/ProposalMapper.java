package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.Proposal;

/**
 * @Author juzix
 * @Date 2022/8/10 17:32
 * @Version 
 * @Desc 
 *******************************
 */
public interface ProposalMapper {
    int deleteByPrimaryKey(String id);

    int insert(Proposal record);

    int insertSelective(Proposal record);

    Proposal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Proposal record);

    int updateByPrimaryKey(Proposal record);
}