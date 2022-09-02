package com.platon.datum.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.exception.ValidateException;
import com.platon.datum.admin.common.util.DidUtil;
import com.platon.datum.admin.dao.AuthorityBusinessMapper;
import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.GlobalOrgMapper;
import com.platon.datum.admin.dao.ProposalMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.dao.entity.AuthorityBusiness;
import com.platon.datum.admin.dao.entity.GlobalOrg;
import com.platon.datum.admin.dao.entity.Proposal;
import com.platon.datum.admin.grpc.client.ProposalClient;
import com.platon.datum.admin.service.IpfsOpService;
import com.platon.datum.admin.service.ProposalService;
import com.platon.datum.admin.service.entity.ProposalMaterialContent;
import com.platon.datum.admin.service.web3j.PlatONClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2022/7/22 16:55
 * @Version
 * @Desc
 */

@Service
@Slf4j
public class ProposalServiceImpl implements ProposalService {

    @Resource
    private ProposalMapper proposalMapper;
    @Resource
    private IpfsOpService ipfsOpService;
    @Resource
    private ProposalClient proposalClient;
    @Resource
    private AuthorityMapper authorityMapper;
    @Resource
    private GlobalOrgMapper globalOrgMapper;
    @Resource
    private AuthorityBusinessMapper authorityBusinessMapper;
    @Resource
    private PlatONClient platONClient;

    private static Map<BigInteger, BigInteger> timeCache = new ConcurrentHashMap<>();

    /**
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public Page<Proposal> getMyProposalList(Integer pageNumber, Integer pageSize, String keyword) {
        Page<Proposal> proposalPage = PageHelper.startPage(pageNumber, pageSize);
        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
        proposalMapper.selectBySubmitter(localOrgIdentityId, keyword);

        // 查询当前块高
        BigInteger curBn = platONClient.platonBlockNumber();
        // 查询平均出块时间
        BigInteger avgPackTime = platONClient.getAvgPackTime();

        proposalPage.forEach(proposal -> {
            boolean changed = convertProposalStatus(curBn, proposal);
            if (changed) {
                proposalMapper.updateStatus(proposal.getId(), proposal.getStatus());
            }

            //添加附加信息
            Map dynamicFields = proposal.getDynamicFields();
            dynamicFields.put("submissionTime", bn2Date(proposal.getSubmissionBn(), curBn, avgPackTime));
            dynamicFields.put("voteBeginTime", bn2Date(proposal.getVoteBeginBn(), curBn, avgPackTime));
            dynamicFields.put("voteEndTime", bn2Date(proposal.getVoteEndBn(), curBn, avgPackTime));
        });
        return proposalPage;
    }

    private Long bn2Date(String bn, BigInteger curBn, BigInteger avgPackTime) {
        if (StrUtil.isBlank(bn)) {
            return null;
        }
        BigInteger convertBn = new BigInteger(bn);
        int comp = convertBn.compareTo(curBn);
        if (comp > 0) {
            // 需要通过预估获得时间
            Long curBnTime = getTimeByBn(curBn);
            return curBnTime + (convertBn.subtract(curBn).multiply(avgPackTime)).longValue();
        } else {
            // 已经过去的块高
            return getTimeByBn(convertBn);
        }
    }

    private Long getTimeByBn(BigInteger bn) {
        BigInteger time = timeCache.computeIfAbsent(bn, k -> platONClient.platonGetBlockByNumber(k).getTimestamp());
        return time.longValue() * 1000;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Proposal getProposalDetail(String id) {
        Proposal proposal = proposalMapper.selectByPrimaryKey(id);

        // 查询当前块高
        BigInteger curBn = platONClient.platonBlockNumber();
        boolean changed = convertProposalStatus(curBn, proposal);
        if (changed) {
            proposalMapper.updateStatus(proposal.getId(), proposal.getStatus());
        }

        // 查询平均出块时间
        BigInteger avgPackTime = platONClient.getAvgPackTime();
        //添加附加信息
        Map dynamicFields = proposal.getDynamicFields();
        dynamicFields.put("submissionTime", bn2Date(proposal.getSubmissionBn(), curBn, avgPackTime));
        dynamicFields.put("voteBeginTime", bn2Date(proposal.getVoteBeginBn(), curBn, avgPackTime));
        dynamicFields.put("voteEndTime", bn2Date(proposal.getVoteEndBn(), curBn, avgPackTime));
        dynamicFields.put("autoQuitTime", bn2Date(proposal.getAutoQuitBn(), curBn, avgPackTime));

        //投票进度=投票赞成数/委员会人数
        if (proposal.getAuthorityNumber() == null) {
            int count = authorityMapper.selectCount();
            proposal.setAuthorityNumber(count);
        }
        return proposal;
    }

    /**
     * @param id
     */
    @Override
    public void revokeProposal(String id) {
        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
        Proposal proposal = proposalMapper.selectByPrimaryKey(id);

        //校验
        if (proposal == null) {
            throw new BizException(Errors.QueryRecordNotExist, "Proposal not exist");
        } else if (!proposal.getSubmitter().equals(localOrgIdentityId)) {
            throw new ValidateException("Proposal not initiated by current organization");
        }

        //当前块高
        BigInteger curBn = platONClient.platonBlockNumber();

        boolean changed = convertProposalStatus(curBn, proposal);
        if (changed) {
            proposalMapper.updateStatus(proposal.getId(), proposal.getStatus());
        }
        Integer status = proposal.getStatus();
        //投票未开始前可以撤回
        if (status == Proposal.StatusEnum.HAS_NOT_STARTED.getStatus()) {
            revoke(proposal);
        } else if (status == Proposal.StatusEnum.EXITING.getStatus()) {
            //是否已经过了撤回时间
            String autoQuitBn = proposal.getAutoQuitBn();
            if (curBn.compareTo(new BigInteger(autoQuitBn)) > 0) {
                throw new BizException(Errors.ProposalRevokeTimeExpired);
            }
            revoke(proposal);
        } else {
            throw new BizException(Errors.ProposalRevokeTimeExpired);
        }
    }

    private void revoke(Proposal proposal) {
        //调用调度服务撤销提案
        boolean success = proposalClient.withdrawProposal(proposal.getId());
        if (!success) {
            throw new BizException(Errors.SysException, "Revoke proposal failed!");
        } else {
            proposalMapper.updateStatus(proposal.getId(), Proposal.StatusEnum.REVOKING.getStatus());
            //将authorityBusiness状态0-未处理设置为状态2-拒绝
            this.rejectProposal(proposal.getId(), proposal.getType());
        }
    }

    private void rejectProposal(String proposalId, int proposalType) {
        //将authorityBusiness状态0-未处理设置为状态2-拒绝
        AuthorityBusiness.TypeEnum typeEnum = proposalType == Proposal.TypeEnum.ADD_AUTHORITY.getValue() ?
                AuthorityBusiness.TypeEnum.JOIN_PROPOSAL : AuthorityBusiness.TypeEnum.KICK_PROPOSAL;
        AuthorityBusiness authorityBusiness = authorityBusinessMapper.selectByTypeAndRelationId(typeEnum.getType(), proposalId);
        if (authorityBusiness != null
                && authorityBusiness.getProcessStatus() == AuthorityBusiness.ProcessStatusEnum.TO_DO.getStatus()) {
            authorityBusinessMapper.updateProcessStatusById(authorityBusiness.getId(), AuthorityBusiness.ProcessStatusEnum.DISAGREE.getStatus());
        }
    }

    /**
     * @param identityId
     * @return
     */
    @Override
    public int getProposalCount(String identityId) {
        int count = proposalMapper.countBySubmitter(identityId);
        return count;
    }


    @Override
    public void nominate(String identityId, String ip, int port, String remark, String material, String materialDesc) {
        Authority authority = authorityMapper.selectByPrimaryKey(identityId);
        //校验
        if (authority != null) {
            throw new BizException(Errors.AuthorityAlreadyExists, "Authority already exist!");
        }
        //如果有已打开的提案则不可再提案
        if (candidateHasOpenProposal(identityId)) {
            throw new BizException(Errors.AnOpenProposalAlreadyExists);
        }
        String address = DidUtil.didToHexAddress(identityId);
        ProposalMaterialContent proposalMaterialContent = new ProposalMaterialContent();
        proposalMaterialContent.setImage(material);
        proposalMaterialContent.setDesc(materialDesc);
        proposalMaterialContent.setRemark(remark);
        String url = ipfsOpService.saveJson(proposalMaterialContent);
        proposalClient.submitProposal(1, url, address, ip + ":" + port);
    }

    /**
     * @param identityId
     * @param remark
     * @param material
     * @param identityId
     */
    @Override
    public void kickOut(String identityId, String remark, String material, String materialDesc) {
        Authority authority = authorityMapper.selectByPrimaryKey(identityId);
        //校验
        if (authority == null) {
            throw new BizException(Errors.AuthorityAlreadyKickOut, "Authority already has been kicked out!");
        }
        if (authority.getIsAdmin() == 1) {
            throw new BizException(Errors.CantKickOutAuthorityAdmin, "Can't kick out authority admin!");
        }
        if (OrgCache.getLocalOrgInfo().getIsAuthority() == 0) {
            throw new BizException(Errors.SysException, "Current org is not authority!");
        }
        //如果有已打开的提案则不可再提案
        if (candidateHasOpenProposal(identityId) || submitterHasOpenProposal(identityId)) {
            throw new BizException(Errors.AnOpenProposalAlreadyExists);
        }

        //上传文件
        ProposalMaterialContent proposalMaterialContent = new ProposalMaterialContent();
        proposalMaterialContent.setImage(material);
        proposalMaterialContent.setDesc(materialDesc);
        proposalMaterialContent.setRemark(remark);
        String url = ipfsOpService.saveJson(proposalMaterialContent);

        //提交提案
        String address = DidUtil.didToHexAddress(identityId);
        proposalClient.submitProposal(2, url, address, "");
    }

    /**
     *
     */
    @Override
    public void exit() {
        Authority authority = authorityMapper.selectByPrimaryKey(OrgCache.getLocalOrgIdentityId());
        if (authority.getIsAdmin() == 1) {
            throw new BizException(Errors.AuthorityAdminCantExit, "Authority admin can't exit!");
        }
        //如果有已打开的提案则不可再提案
        if (candidateHasOpenProposal(OrgCache.getLocalOrgIdentityId())
                || submitterHasOpenProposal(OrgCache.getLocalOrgIdentityId())) {
            throw new BizException(Errors.AnOpenProposalAlreadyExists);
        }
        String observerProxyWalletAddress = OrgCache.getLocalOrgInfo().getObserverProxyWalletAddress();
        proposalClient.submitProposal(3, "", observerProxyWalletAddress, "");
    }

    /**
     * 投票
     */
    @Override
    public void vote(String proposalId) {
        Proposal proposal = proposalMapper.selectByPrimaryKey(proposalId);
        //校验
        if (proposal == null) {
            throw new BizException(Errors.QueryRecordNotExist, "Proposal not exist : " + proposalId);
        }

        //当前块高
        BigInteger curBn = platONClient.platonBlockNumber();

        boolean changed = convertProposalStatus(curBn, proposal);
        if (changed) {
            proposalMapper.updateStatus(proposal.getId(), proposal.getStatus());
        }
        if (proposal.getStatus() == Proposal.StatusEnum.VOTE_START.getStatus()) {
            proposalClient.voteProposal(proposalId);
        } else {
            throw new BizException(Errors.ProposalStatusNotStartVote,
                    "Proposal status is not VOTE_START : " + Proposal.StatusEnum.find(proposal.getStatus()));
        }
    }

    /**
     * @param file
     */
    @Override
    public String upload(MultipartFile file) {
        String url = ipfsOpService.saveFile(file);
        return url;
    }

    /**
     * 将投票未开始的状态转换为投票中的状态
     *
     * @param proposal
     * @return proposal状态是否发生改变
     */
    @Override
    public boolean convertProposalStatus(BigInteger curBn, Proposal proposal) {
        int oldStatus = proposal.getStatus();
        if (proposal.getStatus() == Proposal.StatusEnum.HAS_NOT_STARTED.getStatus()) {
            //开始投票的块高
            String voteBeginBn = proposal.getVoteBeginBn();
            if (curBn.compareTo(new BigInteger(voteBeginBn)) >= 0) {
                //将投票未开始的状态转换为投票开始的状态
                proposal.setStatus(Proposal.StatusEnum.VOTE_START.getStatus());
            }
        }

        if (proposal.getStatus() == Proposal.StatusEnum.VOTE_START.getStatus()) {
            //结束投票的块高
            String voteEndBn = proposal.getVoteEndBn();
            if (curBn.compareTo(new BigInteger(voteEndBn)) >= 0) {
                //将投票未开始的状态转换为投票开始的状态
                proposal.setStatus(Proposal.StatusEnum.VOTE_END.getStatus());
            }
        }

        int newStatus = proposal.getStatus();
        return oldStatus == newStatus ? true : false;
    }

    /**
     * @return
     */
    @Override
    public List<GlobalOrg> getNominateMember(String keyword) {
        List<GlobalOrg> list = globalOrgMapper.selectNominateMemberList(keyword);
        list = list.stream()
                .filter(globalOrg -> !candidateHasOpenProposal(globalOrg.getIdentityId()))
                .filter(globalOrg -> !submitterHasOpenProposal(globalOrg.getIdentityId()))
                .filter(globalOrg -> !globalOrg.getIdentityId().equalsIgnoreCase(OrgCache.getLocalOrgIdentityId()))
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 查询出当前组织是否存在已打开的提案
     */
    @Override
    public boolean candidateHasOpenProposal(String candidate) {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Proposal.StatusEnum.HAS_NOT_STARTED.getStatus());
        statusList.add(Proposal.StatusEnum.VOTE_START.getStatus());
        statusList.add(Proposal.StatusEnum.VOTE_END.getStatus());
        statusList.add(Proposal.StatusEnum.EXITING.getStatus());
        statusList.add(Proposal.StatusEnum.REVOKING.getStatus());
        List<Proposal> proposals = proposalMapper.selectByCandidateAndStatus(candidate, statusList);
        boolean b = proposals.isEmpty() ? false : true;
        log.debug("candidateHasOpenProposal------>{}", b);
        return b;
    }

    /**
     * 查询出当前组织是否存在已打开的提案
     */
    @Override
    public boolean submitterHasOpenProposal(String submitter) {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Proposal.StatusEnum.HAS_NOT_STARTED.getStatus());
        statusList.add(Proposal.StatusEnum.VOTE_START.getStatus());
        statusList.add(Proposal.StatusEnum.VOTE_END.getStatus());
        statusList.add(Proposal.StatusEnum.EXITING.getStatus());
        statusList.add(Proposal.StatusEnum.REVOKING.getStatus());
        List<Proposal> proposals = proposalMapper.selectBySubmitterAndStatus(submitter, statusList);
        boolean b = proposals.isEmpty() ? false : true;
        log.debug("submitterHasOpenProposal------>{}", b);
        return b;
    }

}
