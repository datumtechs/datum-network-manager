package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.Proposal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author juzix
 * @Date 2022/7/21 12:02
 * @Version
 * @Desc ******************************
 */
public interface ProposalMapper {
    int insert(Proposal record);

    int insertSelective(Proposal record);


    /**
     * 查询出本组织发出的提案
     */
    List<Proposal> selectBySponsor(@Param("sponsor") String sponsor, @Param("keyword") String keyword);

    /**
     * 查询出本组织发出的提案总数
     */
    int selectCountBySponsor(@Param("sponsor") String sponsor);

    Proposal selectById(Integer id);
}