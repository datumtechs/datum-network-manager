package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.ProposalLog;

/**
 * @Author juzix
 * @Date 2022/8/10 17:32
 * @Version 
 * @Desc 
 *******************************
 */
public interface ProposalLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProposalLog record);

    int insertSelective(ProposalLog record);

    ProposalLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProposalLog record);

    int updateByPrimaryKey(ProposalLog record);
}