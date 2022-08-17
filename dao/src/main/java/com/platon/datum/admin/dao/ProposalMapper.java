package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.Proposal;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Author juzix
 * @Date 2022/8/10 17:32
 * @Version
 * @Desc ******************************
 */
public interface ProposalMapper {
    Proposal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Proposal record);

    /**
     * 未结束的提案数量
     *
     * @return
     */
    int unfinishedProposalCount();

    /**
     * 插入或更新proposal
     *
     * @param proposalList
     * @return
     */
    int insertOrUpdateBatch(@Param("proposalList") Collection<Proposal> proposalList);

    List<Proposal> selectBySubmitter(@Param("localOrgIdentityId") String localOrgIdentityId,
                                     @Param("keyword") String keyword);

    List<Proposal> selectBySubmitterAndStatus(@Param("localOrgIdentityId") String localOrgIdentityId, @Param("statusList") List<Integer> statusList);

    int updateStatus(@Param("id") String id,
                     @Param("status") Integer status);

    int countBySubmitter(@Param("identityId") String identityId);
}