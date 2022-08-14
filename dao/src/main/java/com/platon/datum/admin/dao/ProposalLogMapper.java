package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.ProposalLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author juzix
 * @Date 2022/8/10 17:32
 * @Version
 * @Desc ******************************
 */
public interface ProposalLogMapper {

    List<ProposalLog> selectByStatus(int status);

    int deleteByPrimaryKey(Long id);

    int insert(ProposalLog record);

    ProposalLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProposalLog record);

    int countByBnAndTxHashAndLogIndex(@Param("blockNumber") String blockNumber,
                                      @Param("transactionHash") String transactionHash,
                                      @Param("logIndex") String logIndex);

    /**
     * 批量更新状态
     *
     * @param proposalLogList
     */
    int updateListStatus(@Param("proposalLogList") List<ProposalLog> proposalLogList);

    ProposalLog selectLatestOne();

}