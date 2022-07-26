package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dao.ProposalMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Proposal;
import com.platon.datum.admin.service.ProposalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2022/7/22 16:55
 * @Version
 * @Desc
 */

@Service
public class ProposalServiceImpl implements ProposalService {

    @Resource
    private ProposalMapper proposalMapper;

    /**
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public Page<Proposal> getProposalList(Integer pageNumber, Integer pageSize, String keyword) {
        Page<Proposal> proposalPage = PageHelper.startPage(pageNumber, pageSize);
        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
        proposalMapper.selectBySponsor(localOrgIdentityId,keyword);
        return proposalPage;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Proposal getProposalDetail(int id) {
        Proposal proposal = proposalMapper.selectById(id);
        return proposal;
    }

    /**
     * @param id
     */
    @Override
    public void revokeProposal(int id) {
        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
        Proposal proposal = proposalMapper.selectById(id);
        if(proposal == null){
            //TODO 提案为空
            throw new BizException(Errors.SysException);
        } else if(!proposal.getSponsor().equals(localOrgIdentityId)){
            //TODO 不是本组织发起的提案，不能撤销
            throw new BizException(Errors.SysException);
        }
        //调用调度服务撤销提案
    }

    /**
     * @param identityId
     * @return
     */
    @Override
    public int getProposalCount(String identityId) {
        int i = proposalMapper.selectCountBySponsor(identityId);
        return i;
    }
}
