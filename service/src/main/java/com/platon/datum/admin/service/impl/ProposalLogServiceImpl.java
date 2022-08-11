//package com.platon.datum.admin.service.impl;
//
//import cn.hutool.core.util.ObjectUtil;
//import com.alibaba.fastjson.JSON;
//import com.github.pagehelper.Page;
//import com.platon.datum.admin.dao.entity.Proposal;
//import com.platon.datum.admin.dao.entity.ProposalLog;
//import com.platon.datum.admin.service.ProposalLogService;
//import com.platon.datum.admin.service.VoteContract;
//import com.platon.protocol.core.methods.response.Log;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.math.BigInteger;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Service
//public class ProposalLogServiceImpl implements ProposalLogService {
//
//    @Resource
//    private VoteContract voteContract;
//    @Resource
//    private PlatONProperties platONProperties;
//    @Resource
//    private ProposalLogManager proposalLogManager;
//    @Resource
//    private ProposalManager proposalManager;
//    @Resource
//    private PublicityManager publicityManager;
//    @Resource
//    private PlatONClient platONClient;
//
//    private static Map<BigInteger, BigInteger> timeCache = new ConcurrentHashMap<>();
//
//    @Override
//    public void subscribe() {
//        BigInteger begin = new BigInteger("platONProperties.getVoteDeployBlockNumber()");
//        ProposalLog latest = null;
//        if(ObjectUtil.isNotNull(latest)){
//            begin = new BigInteger(latest.getBlockNumber());
//        }
//        voteContract.subscribe(begin).subscribe(oo -> oo.ifPresent(tuple2 -> {
//            if(!proposalLogManager.contain(tuple2.getValue1().getBlockNumber(), tuple2.getValue1().getTransactionHash(), tuple2.getValue1().getLogIndex())){
//                ProposalLog save = new ProposalLog();
//                Log log = tuple2.getValue1();
//                save.setBlockNumber(log.getBlockNumber().toString());
//                save.setTxHash(log.getTransactionHash());
//                save.setLogIndex(log.getLogIndex().toString());
//                if(tuple2.getValue2() instanceof Vote.NewProposalEventResponse ){
//                    Vote.NewProposalEventResponse newProposalEventResponse = (Vote.NewProposalEventResponse) tuple2.getValue2();
//                    save.setType(ProposalLogTypeEnum.NEWPROPOSAL_EVENT);
//                    save.setContent(JSON.toJSONString(newProposalEventResponse));
//                }
//                if(tuple2.getValue2() instanceof Vote.VoteProposalEventResponse ){
//                    Vote.VoteProposalEventResponse newProposalEventResponse = (Vote.VoteProposalEventResponse) tuple2.getValue2();
//                    save.setType(ProposalLogTypeEnum.VOTEPROPOSAL_EVENT);
//                    save.setContent(JSON.toJSONString(newProposalEventResponse));
//                }
//                if(tuple2.getValue2() instanceof Vote.ProposalResultEventResponse ){
//                    Vote.ProposalResultEventResponse newProposalEventResponse = (Vote.ProposalResultEventResponse) tuple2.getValue2();
//                    save.setType(ProposalLogTypeEnum.PROPOSALRESULT_EVENT);
//                    save.setContent(JSON.toJSONString(newProposalEventResponse));
//                }
//                if(tuple2.getValue2() instanceof Vote.WithdrawProposalEventResponse ){
//                    Vote.WithdrawProposalEventResponse newProposalEventResponse = (Vote.WithdrawProposalEventResponse) tuple2.getValue2();
//                    save.setType(ProposalLogTypeEnum.WITHDRAWPROPOSAL_EVENT);
//                    save.setContent(JSON.toJSONString(newProposalEventResponse));
//                }
//                if(tuple2.getValue2() instanceof Vote.AuthorityDeleteEventResponse ){
//                    Vote.AuthorityDeleteEventResponse newProposalEventResponse = (Vote.AuthorityDeleteEventResponse) tuple2.getValue2();
//                    save.setType(ProposalLogTypeEnum.AUTHORITYDELETE_EVENT);
//                    save.setContent(JSON.toJSONString(newProposalEventResponse));
//                }
//
//                if(tuple2.getValue2() instanceof Vote.AuthorityAddEventResponse ){
//                    Vote.AuthorityAddEventResponse newProposalEventResponse = (Vote.AuthorityAddEventResponse) tuple2.getValue2();
//                    save.setType(ProposalLogTypeEnum.AUTHORITYADD_EVENT);
//                    save.setContent(JSON.toJSONString(newProposalEventResponse));
//                }
//                proposalLogManager.save(save);
//            }
//        }));
//    }
//
//    @Override
//    public IPage<Proposal> listProposal(Long current, Long size) {
//        Page<Proposal> page = new Page<>(current, size);
//        IPage<Proposal> iPage = proposalManager.list(page);
//        // 查询当前块高
//        BigInteger curBn = platONClient.platonBlockNumber();
//        // 查询平均出块时间
//        BigInteger avgPackTime  = platONClient.getAvgPackTime();
//        iPage.getRecords().stream()
//                .filter(proposal -> proposal.getType() != ProposalTypeEnum.AUTO_QUIT_AUTHORITY)
//                .forEach(proposal -> {
//                    proposal.setVoteBeginTime(bn2Date(proposal.getVoteBeginBn(), curBn, avgPackTime));
//                    proposal.setVoteEndTime(bn2Date(proposal.getVoteEndBn(), curBn, avgPackTime));
//                }
//                );
//        return iPage;
//    }
//
//    @Override
//    public Proposal getProposalDetails(String id) {
//        Proposal proposal = proposalManager.getDetailsById(id);
//        proposal.setPublicity(publicityManager.getById(proposal.getPublicityId()));
//        return proposal;
//    }
//
//    @Override
//    public List<ProposalLog> listProposalLog(Long latestSynced, Long size) {
//        return proposalLogManager.listByPage(latestSynced, size);
//    }
//
//    @Override
//    @Transactional
//    public boolean saveOrUpdateBatchProposal(Collection<Proposal> proposalList, Set<String> publicityIdSet) {
//        proposalManager.saveOrUpdateBatch(proposalList);
//        if(publicityIdSet.size() > 0 ){
//            publicityManager.saveBatch(publicityIdSet);
//        }
//        return true;
//    }
//
//    @Override
//    public Proposal getProposalById(String id) {
//        return proposalManager.getById(id);
//    }
//
//    private Date bn2Date(String bn, BigInteger curBn, BigInteger avgPackTime){
//        BigInteger convertBn = new BigInteger(bn);
//        int comp = convertBn.compareTo(curBn);
//        if(comp > 0){
//            // 需要通过预估获得时间
//            Date cur = getTimeByBn(convertBn);
//            cur.setTime(cur.getTime() + convertBn.subtract(curBn).multiply(avgPackTime).longValue());
//            return cur;
//        } else {
//            // 已经过去的块高
//            return getTimeByBn(convertBn);
//        }
//    }
//
//    private Date getTimeByBn(BigInteger bn) {
//        BigInteger time = timeCache.computeIfAbsent(bn, k -> platONClient.platonGetBlockByNumber(k).getTimestamp());
//        return new Date(time.longValue());
//    }
//}
