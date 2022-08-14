package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.AuthorityBusinessMapper;
import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.ProposalMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.dao.entity.AuthorityBusiness;
import com.platon.datum.admin.dao.entity.Proposal;
import com.platon.datum.admin.service.ProposalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author liushuyu
 * @Description 刷新委员会成员的事务处理状态
 */

@Slf4j
@Configuration
public class AuthorityBusinessProcessStatusTask {

    @Resource
    private AuthorityBusinessMapper authorityBusinessMapper;
    @Resource
    private ProposalService proposalService;
    @Resource
    private ProposalMapper proposalMapper;
    @Resource
    private AuthorityMapper authorityMapper;

    @Scheduled(fixedDelayString = "${AuthorityBusinessProcessStatusTask.fixedDelay}")
    public void refreshTodoList() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        //1.如果本组织不是委员会成员，则无需关心该任务
        Authority authority = authorityMapper.selectByPrimaryKey(OrgCache.getLocalOrgIdentityId());
        if (authority == null) {
            return;
        }
        log.debug("刷新委员会成员的事务处理状态定时任务开始>>>");
        //1.查询出委员会事务对提案的待办列表
        List<AuthorityBusiness> authorityBusinessList = authorityBusinessMapper.selectProposalTodoList();

        //2.查询待办列表中提案的状态
        authorityBusinessList.forEach(authorityBusiness -> {
            Proposal proposal = getProposal(authorityBusiness);
            //3.如果提案过了投票时间了，则将未处理的事务改成处理状态为拒绝
            if (proposal.getStatus() == Proposal.StatusEnum.VOTE_END.getValue()) {
                authorityBusinessMapper.updateProcessStatusById(authorityBusiness.getId(), AuthorityBusiness.ProcessStatusEnum.DISAGREE.getStatus());
            }
        });
        log.debug("刷新委员会成员的事务处理状态定时任务结束|||");
    }

    private Proposal getProposal(AuthorityBusiness authorityBusiness) {
        String id = authorityBusiness.getRelationId();
        int status = (int) authorityBusiness.getDynamicFields().get("status");
        String submissionBn = (String) authorityBusiness.getDynamicFields().get("submissionBn");
        String voteBeginBn = (String) authorityBusiness.getDynamicFields().get("voteBeginBn");
        String voteEndBn = (String) authorityBusiness.getDynamicFields().get("voteEndBn");
        Proposal proposal = new Proposal();
        proposal.setId(id);
        proposal.setStatus(status);
        proposal.setSubmissionBn(submissionBn);
        proposal.setVoteBeginBn(voteBeginBn);
        proposal.setVoteEndBn(voteEndBn);

        proposalService.convertProposalStatus(proposal);

        Integer newStatus = proposal.getStatus();
        //状态改变了则修改数据库
        if (status != newStatus) {
            proposalMapper.updateStatus(id, newStatus);
        }

        return proposal;
    }


}
