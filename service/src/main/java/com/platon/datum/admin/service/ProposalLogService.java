//package com.platon.datum.admin.service;
//
//
//import com.platon.datum.admin.dao.entity.Proposal;
//import com.platon.datum.admin.dao.entity.ProposalLog;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//
//public interface ProposalLogService {
//    void subscribe();
//
//    IPage<Proposal> listProposal(Long current, Long size);
//
//    Proposal getProposalDetails(String id);
//
//    List<ProposalLog> listProposalLog(Long latestSynced, Long size);
//
//    boolean saveOrUpdateBatchProposal(Collection<Proposal> proposalList, Set<String> publicityIdSet);
//
//    Proposal getProposalById(String id);
//}
