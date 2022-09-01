package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.ProposalLogMapper;
import com.platon.datum.admin.dao.ProposalMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.dao.entity.Proposal;
import com.platon.datum.admin.grpc.client.ProposalClient;
import com.platon.datum.admin.service.ProposalLogService;
import com.platon.datum.admin.service.ProposalService;
import com.platon.datum.admin.service.web3j.PlatONClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
    private AuthorityMapper authorityMapper;
    @Resource
    private ProposalClient proposalClient;
    @Resource
    private ProposalService proposalService;
    @Resource
    private PlatONClient platONClient;
    @Resource
    private ProposalLogService proposalLogService;

    /**
     * 刷新提案状态
     */
    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(fixedDelayString = "${ProposalRefreshTask.fixedDelay}")
    public void refreshProposal() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        Authority authority = authorityMapper.selectByPrimaryKey(OrgCache.getLocalOrgIdentityId());
        //如果不是委员会成员则不关心提案状态
        if (authority == null) {
            return;
        }

        log.debug("刷新提案状态定时任务开始>>>");
        proposalLogService.processTodoProposalLog();
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
        statusList.add(Proposal.StatusEnum.HAS_NOT_STARTED.getStatus());
        statusList.add(Proposal.StatusEnum.VOTE_START.getStatus());
        statusList.add(Proposal.StatusEnum.VOTE_END.getStatus());
        List<Proposal> proposalList = proposalMapper.selectBySubmitterAndStatus(localOrgIdentityId, statusList);

        BigInteger curBn = platONClient.platonBlockNumber();

        proposalList = proposalList.stream()
                .map(proposal -> {
                    boolean changed = proposalService.convertProposalStatus(curBn, proposal);
                    if (changed) {
                        proposalMapper.updateStatus(proposal.getId(), proposal.getStatus());
                    }
                    return proposal;
                })
                .filter(proposal -> proposal.getStatus() == Proposal.StatusEnum.VOTE_END.getStatus())
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

        //处理主动退出的proposal
        processExitingProposal();

        log.debug("提案effect定时任务结束|||");
    }

    private void processExitingProposal() {
        //1.获取主动退出中的提案
        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Proposal.StatusEnum.EXITING.getStatus());
        List<Proposal> proposalList = proposalMapper.selectBySubmitterAndStatus(localOrgIdentityId, statusList);

        BigInteger curBn = platONClient.platonBlockNumber();

        proposalList.stream()
                //2.只处理到了生效时间还未生效的提案
                .filter(proposal -> curBn.compareTo(new BigInteger(proposal.getAutoQuitBn())) >= 0)
                .forEach(proposal -> {
                    String proposalId = proposal.getId();
                    //3.调用effect
                    try {
                        proposalClient.effectProposal(proposalId);
                    } catch (Throwable exception) {
                        log.error("Call proposalClient effectProposal failed!", exception);
                    }
                });
    }

}
