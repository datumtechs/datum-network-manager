package com.platon.datum.admin.service.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.AuthorityBusinessMapper;
import com.platon.datum.admin.dao.ProposalLogMapper;
import com.platon.datum.admin.dao.ProposalMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.AuthorityBusiness;
import com.platon.datum.admin.dao.entity.Proposal;
import com.platon.datum.admin.dao.entity.ProposalLog;
import com.platon.datum.admin.grpc.client.ProposalClient;
import com.platon.datum.admin.service.IpfsOpService;
import com.platon.datum.admin.service.ProposalService;
import com.platon.datum.admin.service.VoteContract;
import com.platon.datum.admin.service.entity.ProposalMaterialContent;
import com.platon.datum.admin.service.entity.VoteConfig;
import com.platon.datum.admin.service.web3j.PlatONClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liushuyu
 * @date 2022/8/11 14:00
 * @desc 刷新提案状态
 */

@Slf4j
@Configuration
public class ProposalRefreshTask {

    @Resource
    private ProposalLogMapper proposalLogMapper;
    @Resource
    private ProposalMapper proposalMapper;
    @Resource
    private AuthorityBusinessMapper authorityBusinessMapper;
    @Resource
    private VoteContract voteContract;
    @Resource
    private IpfsOpService ipfsOpService;
    @Resource
    private ProposalClient proposalClient;
    @Resource
    private ProposalService proposalService;
    @Resource
    private PlatONClient platONClient;

    /**
     * 刷新提案状态
     */
    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(fixedDelayString = "${ProposalRefreshTask.fixedDelay}")
    public void refreshProposal() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        log.debug("刷新提案状态定时任务开始>>>");
        List<ProposalLog> proposalLogList = proposalLogMapper.selectByStatus(ProposalLog.StatusEnum.TODO.getValue());

        if (CollectionUtil.isEmpty(proposalLogList)) {
            return;
        }
        //日志分析
        analyzeProposalLog(proposalLogList);

        log.debug("刷新提案状态定时任务结束|||");
    }

    /**
     * 将投票完的提案进行生效，需要调用effect方法
     */
    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(fixedDelayString = "${ProposalRefreshTask.fixedDelay}")
    public void effectProposal() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        log.debug("提案effect定时任务开始>>>");
        //1.获取投票完的提案列表
        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Proposal.StatusEnum.HAS_NOT_STARTED.getValue());
        statusList.add(Proposal.StatusEnum.VOTE_START.getValue());
        statusList.add(Proposal.StatusEnum.VOTE_END.getValue());
        List<Proposal> proposalList = proposalMapper.selectBySubmitterAndStatus(localOrgIdentityId, statusList);

        BigInteger curBn = platONClient.platonBlockNumber();

        proposalList = proposalList.stream()
                .map(proposal -> {
                    boolean changed = proposalService.convertProposalStatus(curBn, proposal);
                    if(changed){
                        proposalMapper.updateStatus(proposal.getId(), proposal.getStatus());
                    }
                    return proposal;
                })
                .filter(proposal -> proposal.getStatus() == Proposal.StatusEnum.VOTE_END.getValue())
                .collect(Collectors.toList());
        //2.调用effect方法生效
        proposalList.forEach(proposal -> {
            String proposalId = proposal.getId();
            try {
                proposalClient.effectProposal(proposalId);
            } catch (Throwable exception) {
                log.error("Call proposalClient effectProposal failed!", exception);
            }
        });
        log.debug("提案effect定时任务结束|||");
    }

    private void analyzeProposalLog(List<ProposalLog> proposalLogList) {
        Map<String, Proposal> saveMap = new HashMap<>();
        VoteConfig voteConfig = voteContract.getConfig();

        List<AuthorityBusiness> authorityBusinessList = new ArrayList<>();

        proposalLogList.forEach(proposalLog -> {
            JSONObject contentJsonObject = JSONUtil.parseObj(proposalLog.getContent());
            AuthorityBusiness authorityBusiness = null;
            // 提交提案
            if (proposalLog.getType() == ProposalLog.TypeEnum.NEWPROPOSAL_EVENT.getValue()) {
                Proposal proposal = new Proposal();
                proposal.setId(contentJsonObject.getStr("proposalId"));
                proposal.setSubmitter(contentJsonObject.getStr("submitter"));
                proposal.setCandidate(contentJsonObject.getStr("candidate"));
                proposal.setSubmissionBn(contentJsonObject.getStr("submitBlockNo"));
                proposal.setStatus(Proposal.StatusEnum.HAS_NOT_STARTED.getValue());

                //将公示信息拿出来放到数据库中
                String proposalUrl = contentJsonObject.getStr("proposalUrl");
                ProposalMaterialContent materialContent = ipfsOpService.queryJson(proposalUrl, ProposalMaterialContent.class);
                proposal.setRemark(materialContent.getRemark());
                proposal.setMaterial(materialContent.getImage());
                proposal.setMaterialDesc(materialContent.getDesc());

                // 增加委员会成员
                if (contentJsonObject.getInt("proposalType") == 1) {
                    proposal.setType(Proposal.TypeEnum.ADD_AUTHORITY.getValue());
                    proposal.setVoteBeginBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getBeginVote()).toString());
                    proposal.setVoteEndBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getBeginVote()).add(voteConfig.getVote()).toString());
                    proposal.setVoteAgreeNumber(0);

                    authorityBusiness = getAuthorityBusiness(proposal);
                }
                // 踢出委员会成员
                if (contentJsonObject.getInt("proposalType") == 2) {
                    proposal.setType(Proposal.TypeEnum.KICK_OUT_AUTHORITY.getValue());
                    proposal.setVoteBeginBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getBeginVote()).toString());
                    proposal.setVoteEndBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getBeginVote()).add(voteConfig.getVote()).toString());
                    proposal.setVoteAgreeNumber(0);

                    authorityBusiness = getAuthorityBusiness(proposal);
                }
                // 主动退出
                if (contentJsonObject.getInt("proposalType") == 3) {
                    proposal.setType(Proposal.TypeEnum.AUTO_QUIT_AUTHORITY.getValue());
                    proposal.setAutoQuitBn(new BigInteger(proposal.getSubmissionBn()).add(voteConfig.getQuit()).toString());
                    proposal.setStatus(Proposal.StatusEnum.EXITING.getValue());
                }
                saveMap.put(proposal.getId(), proposal);
            }

            // 撤销提案
            if (proposalLog.getType() == ProposalLog.TypeEnum.WITHDRAWPROPOSAL_EVENT.getValue()) {
                //查询出指定的提案，并将状态修改为撤销状态
                Proposal proposal = saveMap.computeIfAbsent(contentJsonObject.getStr("proposalId"), id -> proposalMapper.selectByPrimaryKey(id));
                proposal.setStatus(Proposal.StatusEnum.REVOKED.getValue());
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
                proposal.setStatus(contentJsonObject.getBool("result") ? Proposal.StatusEnum.VOTE_PASS.getValue() : Proposal.StatusEnum.VOTE_NOT_PASS.getValue());
                proposal.setAuthorityNumber(voteContract.sizeOfAllAuthority(new BigInteger(proposalLog.getBlockNumber()).subtract(BigInteger.ONE)));
            }
            proposalLog.setStatus(ProposalLog.StatusEnum.DONE.getValue());

            if (authorityBusiness != null) {
                authorityBusinessList.add(authorityBusiness);
            }

        });
        //更新Proposal表
        proposalMapper.insertOrUpdateBatch(saveMap.values());
        //更新Proposal log表
        proposalLogMapper.updateListStatus(proposalLogList);

        //更新委员会事务表authorityBusiness
        authorityBusinessList.forEach(authorityBusiness -> {
            authorityBusinessMapper.insertSelectiveReturnId(authorityBusiness);
        });
    }


    private AuthorityBusiness getAuthorityBusiness(Proposal proposal) {
        AuthorityBusiness authorityBusiness = new AuthorityBusiness();
        if (Proposal.TypeEnum.KICK_OUT_AUTHORITY.getValue() == proposal.getType()) {
            authorityBusiness.setType(AuthorityBusiness.TypeEnum.KICK_PROPOSAL.getType());
        } else if (Proposal.TypeEnum.ADD_AUTHORITY.getValue() == proposal.getType()) {
            authorityBusiness.setType(AuthorityBusiness.TypeEnum.JOIN_PROPOSAL.getType());
        } else {
            return null;
        }
        authorityBusiness.setRelationId(proposal.getId());
        authorityBusiness.setApplyOrg(proposal.getSubmitter());
        authorityBusiness.setSpecifyOrg(proposal.getCandidate());
        authorityBusiness.setStartTime(LocalDateTimeUtil.now());
        authorityBusiness.setProcessStatus(AuthorityBusiness.ProcessStatusEnum.TO_DO.getStatus());
        return authorityBusiness;
    }

}
