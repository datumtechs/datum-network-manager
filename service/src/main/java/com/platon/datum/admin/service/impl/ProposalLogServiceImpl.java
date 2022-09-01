package com.platon.datum.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.platon.datum.admin.common.util.DidUtil;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.AuthorityBusinessMapper;
import com.platon.datum.admin.dao.ProposalLogMapper;
import com.platon.datum.admin.dao.ProposalMapper;
import com.platon.datum.admin.dao.entity.AuthorityBusiness;
import com.platon.datum.admin.dao.entity.Proposal;
import com.platon.datum.admin.dao.entity.ProposalLog;
import com.platon.datum.admin.dao.entity.SysConfig;
import com.platon.datum.admin.service.*;
import com.platon.datum.admin.service.entity.ProposalMaterialContent;
import com.platon.datum.admin.service.entity.VoteConfig;
import com.platon.datum.admin.service.evm.Vote;
import com.platon.datum.admin.service.web3j.PlatONClient;
import com.platon.protocol.core.methods.response.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

/**
 * @author liushuyu
 */

@Slf4j
@Service
public class ProposalLogServiceImpl implements ProposalLogService {

    @Resource
    private VoteContract voteContract;
    @Resource
    private ProposalLogMapper proposalLogMapper;
    @Resource
    private ProposalMapper proposalMapper;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private IpfsOpService ipfsOpService;
    @Resource
    private AuthorityBusinessMapper authorityBusinessMapper;
    @Resource
    private PlatONClient platONClient;
    @Resource
    private ProposalService proposalService;

    @Override
    public void subscribe() {
        SysConfig config = sysConfigService.getConfig(SysConfig.KeyEnum.VOTE_CONTRACT_DEPLOY_BN.getKey());
        BigInteger begin = config == null ? BigInteger.ONE : new BigInteger(StrUtil.trim(config.getValue()));
        ProposalLog latest = proposalLogMapper.selectLatestOne();
        if (ObjectUtil.isNotNull(latest)) {
            begin = new BigInteger(latest.getBlockNumber());
        }
        voteContract.subscribe(begin).subscribe(oo -> oo.ifPresent(tuple2 -> {
//            //如果是委员会成员，或者退出委员会后有未结束的提案才会继续往下走
//            try {
//                Org localOrgInfo = OrgCache.getLocalOrgInfo();
//                boolean isAuthority = localOrgInfo.getIsAuthority() == 1;
//                boolean hasUnfinishedProposal = proposalMapper.unfinishedProposalCount() > 0;
//                if (isAuthority || hasUnfinishedProposal) {
//                    log.debug("isAuthority or hasUnfinishedProposal==>{} or {}", isAuthority, hasUnfinishedProposal);
//                } else {
//                    return;
//                }
//            } catch (OrgInfoNotFound ex) {
//                log.warn(ex.getErrorMessage());
//                return;
//            }
            if (proposalLogMapper.countByBnAndTxHashAndLogIndex(tuple2.getValue1().getBlockNumber().toString(),
                    tuple2.getValue1().getTransactionHash(),
                    tuple2.getValue1().getLogIndex().toString()) <= 0) {
                ProposalLog save = new ProposalLog();
                Log log = tuple2.getValue1();
                save.setBlockNumber(log.getBlockNumber().toString());
                save.setTxHash(log.getTransactionHash());
                save.setLogIndex(log.getLogIndex().toString());
                if (tuple2.getValue2() instanceof Vote.NewProposalEventResponse) {
                    Vote.NewProposalEventResponse newProposalEventResponse = (Vote.NewProposalEventResponse) tuple2.getValue2();
                    save.setType(ProposalLog.TypeEnum.NEWPROPOSAL_EVENT.getValue());
                    save.setContent(JSON.toJSONString(newProposalEventResponse));
                }
                if (tuple2.getValue2() instanceof Vote.VoteProposalEventResponse) {
                    Vote.VoteProposalEventResponse newProposalEventResponse = (Vote.VoteProposalEventResponse) tuple2.getValue2();
                    save.setType(ProposalLog.TypeEnum.VOTEPROPOSAL_EVENT.getValue());
                    save.setContent(JSON.toJSONString(newProposalEventResponse));
                }
                if (tuple2.getValue2() instanceof Vote.ProposalResultEventResponse) {
                    Vote.ProposalResultEventResponse newProposalEventResponse = (Vote.ProposalResultEventResponse) tuple2.getValue2();
                    save.setType(ProposalLog.TypeEnum.PROPOSALRESULT_EVENT.getValue());
                    save.setContent(JSON.toJSONString(newProposalEventResponse));
                }
                if (tuple2.getValue2() instanceof Vote.WithdrawProposalEventResponse) {
                    Vote.WithdrawProposalEventResponse newProposalEventResponse = (Vote.WithdrawProposalEventResponse) tuple2.getValue2();
                    save.setType(ProposalLog.TypeEnum.WITHDRAWPROPOSAL_EVENT.getValue());
                    save.setContent(JSON.toJSONString(newProposalEventResponse));
                }
                if (tuple2.getValue2() instanceof Vote.AuthorityDeleteEventResponse) {
                    Vote.AuthorityDeleteEventResponse newProposalEventResponse = (Vote.AuthorityDeleteEventResponse) tuple2.getValue2();
                    save.setType(ProposalLog.TypeEnum.AUTHORITYDELETE_EVENT.getValue());
                    save.setContent(JSON.toJSONString(newProposalEventResponse));
                }

                if (tuple2.getValue2() instanceof Vote.AuthorityAddEventResponse) {
                    Vote.AuthorityAddEventResponse newProposalEventResponse = (Vote.AuthorityAddEventResponse) tuple2.getValue2();
                    save.setType(ProposalLog.TypeEnum.AUTHORITYADD_EVENT.getValue());
                    save.setContent(JSON.toJSONString(newProposalEventResponse));
                }
                proposalLogMapper.insert(save);
            }
        }));
    }

    /**
     *
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void processTodoProposalLog() {
        List<ProposalLog> proposalLogList = proposalLogMapper.selectByStatus(ProposalLog.StatusEnum.TODO.getValue());

        if (CollectionUtil.isEmpty(proposalLogList)) {
            return;
        }
        //日志分析
        analyzeProposalLog(proposalLogList);
    }

    private void analyzeProposalLog(List<ProposalLog> proposalLogList) {
        Map<String, Proposal> saveMap = new HashMap<>();
        VoteConfig voteConfig = voteContract.getConfig();

        List<AuthorityBusiness> insertAuthorityBusinessList = new ArrayList<>();

        //被撤销的提案
        Set<String> withdrawProposalId = new HashSet<>();

        BigInteger curBn = platONClient.platonBlockNumber();

        proposalLogList.forEach(proposalLog -> {
            JSONObject contentJsonObject = JSONUtil.parseObj(proposalLog.getContent());
            AuthorityBusiness insertAuthorityBusiness = null;
            // 提交提案
            if (proposalLog.getType() == ProposalLog.TypeEnum.NEWPROPOSAL_EVENT.getValue()) {
                Proposal proposal = new Proposal();
                proposal.setId(contentJsonObject.getStr("proposalId"));
                proposal.setSubmitter(DidUtil.addressToDid(contentJsonObject.getStr("submitter")));
                proposal.setCandidate(DidUtil.addressToDid(contentJsonObject.getStr("candidate")));
                proposal.setSubmissionBn(contentJsonObject.getStr("submitBlockNo"));
                proposal.setStatus(Proposal.StatusEnum.HAS_NOT_STARTED.getStatus());

                //将公示信息拿出来放到数据库中
                String proposalUrl = contentJsonObject.getStr("proposalUrl");
                if (StrUtil.isNotBlank(proposalUrl)) {
                    ProposalMaterialContent materialContent = ipfsOpService.queryJson(proposalUrl, ProposalMaterialContent.class);
                    proposal.setRemark(materialContent.getRemark());
                    proposal.setMaterial(materialContent.getImage());
                    proposal.setMaterialDesc(materialContent.getDesc());
                }

                // 增加委员会成员
                if (contentJsonObject.getInt("proposalType") == 1) {
                    proposal.setType(Proposal.TypeEnum.ADD_AUTHORITY.getValue());
                    proposal.setVoteBeginBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getBeginVote()).toString());
                    proposal.setVoteEndBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getBeginVote()).add(voteConfig.getVote()).toString());
                    proposal.setVoteAgreeNumber(0);

                    insertAuthorityBusiness = getInsertAuthorityBusiness(curBn, proposal);
                }
                // 踢出委员会成员
                if (contentJsonObject.getInt("proposalType") == 2) {
                    proposal.setType(Proposal.TypeEnum.KICK_OUT_AUTHORITY.getValue());
                    proposal.setVoteBeginBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getBeginVote()).toString());
                    proposal.setVoteEndBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getBeginVote()).add(voteConfig.getVote()).toString());
                    proposal.setVoteAgreeNumber(0);

                    insertAuthorityBusiness = getInsertAuthorityBusiness(curBn, proposal);
                }
                // 主动退出
                if (contentJsonObject.getInt("proposalType") == 3) {
                    proposal.setType(Proposal.TypeEnum.AUTO_QUIT_AUTHORITY.getValue());
                    proposal.setAutoQuitBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getQuit()).toString());
                    proposal.setStatus(Proposal.StatusEnum.EXITING.getStatus());
                }
                saveMap.put(proposal.getId(), proposal);
            }

            // 撤销提案
            if (proposalLog.getType() == ProposalLog.TypeEnum.WITHDRAWPROPOSAL_EVENT.getValue()) {
                //查询出指定的提案，并将状态修改为撤销状态
                Proposal proposal = saveMap.computeIfAbsent(contentJsonObject.getStr("proposalId"), id -> proposalMapper.selectByPrimaryKey(id));
                proposal.setStatus(Proposal.StatusEnum.REVOKED.getStatus());
                //删除指定的委员会事务
                withdrawProposalId.add(proposal.getId());
            }

            // 对提案投票
            if (proposalLog.getType() == ProposalLog.TypeEnum.VOTEPROPOSAL_EVENT.getValue()) {
                //查询出指定的提案，并将投票数+1
                Proposal proposal = saveMap.computeIfAbsent(contentJsonObject.getStr("proposalId"), id -> proposalMapper.selectByPrimaryKey(id));
                proposal.setVoteAgreeNumber(proposal.getVoteAgreeNumber() + 1);
            }

            // 投票结果
            if (proposalLog.getType() == ProposalLog.TypeEnum.PROPOSALRESULT_EVENT.getValue()) {
                //查询出指定的提案，并将状态修改为投票通过或者投票不通过
                Proposal proposal = saveMap.computeIfAbsent(contentJsonObject.getStr("proposalId"), id -> proposalMapper.selectByPrimaryKey(id));
                if (proposal.getType() == Proposal.TypeEnum.AUTO_QUIT_AUTHORITY.getValue()) {
                    proposal.setStatus(Proposal.StatusEnum.SIGNED_OUT.getStatus());
                } else {
                    proposal.setStatus(contentJsonObject.getBool("result") ? Proposal.StatusEnum.VOTE_PASS.getStatus() : Proposal.StatusEnum.VOTE_NOT_PASS.getStatus());
                }
                proposal.setAuthorityNumber(voteContract.sizeOfAllAuthority(new BigInteger(proposalLog.getBlockNumber()).subtract(BigInteger.ONE)));
            }
            proposalLog.setStatus(ProposalLog.StatusEnum.DONE.getValue());

            if (insertAuthorityBusiness != null) {
                insertAuthorityBusinessList.add(insertAuthorityBusiness);
            }

        });
        //更新Proposal表
        proposalMapper.insertOrUpdateBatch(saveMap.values());
        //更新Proposal log表
        proposalLogMapper.updateListStatus(proposalLogList);

        //更新委员会事务表authorityBusiness
        insertAuthorityBusinessList.forEach(authorityBusiness -> {
            authorityBusinessMapper.insertSelectiveReturnId(authorityBusiness);
        });

        //撤回的提案不显示在待办事务和已办事务中
        if (!withdrawProposalId.isEmpty()) {
            authorityBusinessMapper.deleteWithdrawProposalByProposalIds(withdrawProposalId);
        }
    }

    private AuthorityBusiness getInsertAuthorityBusiness(BigInteger curBn, Proposal proposal) {
        AuthorityBusiness authorityBusiness = new AuthorityBusiness();
        //1.只有加入和踢出的提案需要投票。则会加入到委员会事务中
        if (Proposal.TypeEnum.KICK_OUT_AUTHORITY.getValue() == proposal.getType()) {
            authorityBusiness.setType(AuthorityBusiness.TypeEnum.KICK_PROPOSAL.getType());
        } else if (Proposal.TypeEnum.ADD_AUTHORITY.getValue() == proposal.getType()) {
            authorityBusiness.setType(AuthorityBusiness.TypeEnum.JOIN_PROPOSAL.getType());
        } else {
            return null;
        }

        //2.如果过了提案投票时间的，则不需要加入到委员会事务中
        proposalService.convertProposalStatus(curBn, proposal);
        if (proposal.getStatus() == Proposal.StatusEnum.HAS_NOT_STARTED.getStatus()
                || proposal.getStatus() == Proposal.StatusEnum.VOTE_START.getStatus()) {
            authorityBusiness.setRelationId(proposal.getId());
            authorityBusiness.setApplyOrg(proposal.getSubmitter());
            authorityBusiness.setSpecifyOrg(proposal.getCandidate());
            authorityBusiness.setStartTime(LocalDateTimeUtil.now());
            authorityBusiness.setProcessStatus(AuthorityBusiness.ProcessStatusEnum.TO_DO.getStatus());
            return authorityBusiness;
        } else {
            return null;
        }
    }
}
