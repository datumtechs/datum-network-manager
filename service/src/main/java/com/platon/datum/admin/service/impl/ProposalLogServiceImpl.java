package com.platon.datum.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.platon.datum.admin.common.exception.OrgInfoNotFound;
import com.platon.datum.admin.dao.ProposalLogMapper;
import com.platon.datum.admin.dao.ProposalMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dao.entity.ProposalLog;
import com.platon.datum.admin.dao.entity.SysConfig;
import com.platon.datum.admin.service.ProposalLogService;
import com.platon.datum.admin.service.SysConfigService;
import com.platon.datum.admin.service.VoteContract;
import com.platon.datum.admin.service.evm.Vote;
import com.platon.protocol.core.methods.response.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

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

    @Override
    public void subscribe() {
        SysConfig config = sysConfigService.getConfig(SysConfig.KeyEnum.VOTE_CONTRACT_DEPLOY_BN.getKey());
        BigInteger begin = config == null ? BigInteger.ONE : new BigInteger(StrUtil.trim(config.getValue()));
        ProposalLog latest = proposalLogMapper.selectLatestOne();
        if (ObjectUtil.isNotNull(latest)) {
            begin = new BigInteger(latest.getBlockNumber());
        }
        voteContract.subscribe(begin).subscribe(oo -> oo.ifPresent(tuple2 -> {
            //如果是委员会成员，或者退出委员会后有未结束的提案才会继续往下走
            try {
                Org localOrgInfo = OrgCache.getLocalOrgInfo();
                boolean isAuthority = localOrgInfo.getIsAuthority() == 1;
                boolean hasUnfinishedProposal = proposalMapper.unfinishedProposalCount() > 0;
                if (isAuthority || hasUnfinishedProposal) {
                    log.debug("isAuthority or hasUnfinishedProposal==>{} or {}", isAuthority, hasUnfinishedProposal);
                } else {
                    return;
                }
            } catch (OrgInfoNotFound ex) {
                log.warn(ex.getErrorMessage());
                return;
            }
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
}
