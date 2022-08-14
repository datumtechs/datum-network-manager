package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.exception.ValidateException;
import com.platon.datum.admin.common.util.DidUtil;
import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.ProposalMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.dao.entity.Proposal;
import com.platon.datum.admin.grpc.client.ProposalClient;
import com.platon.datum.admin.service.IpfsOpService;
import com.platon.datum.admin.service.ProposalService;
import com.platon.datum.admin.service.entity.ProposalMaterialContent;
import com.platon.datum.admin.service.web3j.PlatONClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    @Resource
    private IpfsOpService ipfsOpService;
    @Resource
    private ProposalClient proposalClient;
    @Resource
    private AuthorityMapper authorityMapper;
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
            convertProposalStatus(proposal);
            proposalMapper.updateStatus(proposal.getId(), proposal.getStatus());

            //添加附加信息
            Map dynamicFields = proposal.getDynamicFields();
            dynamicFields.put("submissionTime", bn2Date(proposal.getSubmissionBn(), curBn, avgPackTime));
            dynamicFields.put("voteBeginTime", bn2Date(proposal.getVoteBeginBn(), curBn, avgPackTime));
            dynamicFields.put("voteEndTime", bn2Date(proposal.getVoteEndBn(), curBn, avgPackTime));
        });
        return proposalPage;
    }

    private Long bn2Date(String bn, BigInteger curBn, BigInteger avgPackTime) {
        BigInteger convertBn = new BigInteger(bn);
        int comp = convertBn.compareTo(curBn);
        if (comp > 0) {
            // 需要通过预估获得时间
            Long curBnTime = getTimeByBn(curBn);
            return curBnTime + avgPackTime.longValue();
        } else {
            // 已经过去的块高
            return getTimeByBn(convertBn);
        }
    }

    private Long getTimeByBn(BigInteger bn) {
        BigInteger time = timeCache.computeIfAbsent(bn, k -> platONClient.platonGetBlockByNumber(k).getTimestamp());
        return time.longValue();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Proposal getProposalDetail(String id) {
        Proposal proposal = proposalMapper.selectByPrimaryKey(id);
        convertProposalStatus(proposal);
        proposalMapper.updateStatus(proposal.getId(), proposal.getStatus());

        // 查询当前块高
        BigInteger curBn = platONClient.platonBlockNumber();
        // 查询平均出块时间
        BigInteger avgPackTime = platONClient.getAvgPackTime();
        //添加附加信息
        Map dynamicFields = proposal.getDynamicFields();
        dynamicFields.put("submissionTime", bn2Date(proposal.getSubmissionBn(), curBn, avgPackTime));
        dynamicFields.put("voteBeginTime", bn2Date(proposal.getVoteBeginBn(), curBn, avgPackTime));
        dynamicFields.put("voteEndTime", bn2Date(proposal.getVoteEndBn(), curBn, avgPackTime));

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

        convertProposalStatus(proposal);
        Integer status = proposal.getStatus();
        //投票未开始前可以投票
        if (status == Proposal.StatusEnum.HAS_NOT_STARTED.getValue()) {
            //调用调度服务撤销提案
            boolean success = proposalClient.withdrawProposal(String.valueOf(id));
            if (!success) {
                throw new BizException(Errors.SysException, "Revoke proposal failed!");
            } else {
                proposalMapper.updateStatus(id, Proposal.StatusEnum.REVOKING.getValue());
            }
        } else {
            throw new ValidateException("Revoke time has passed!");
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
            throw new BizException(Errors.QueryRecordNotExist, "Authority not exist");
        }
        if (OrgCache.getLocalOrgInfo().getIsAuthority() == 0) {
            throw new BizException(Errors.SysException, "Current org is not authority");
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
        convertProposalStatus(proposal);
        if (proposal.getStatus() == Proposal.StatusEnum.VOTE_START.getValue()) {
            proposalClient.voteProposal(proposalId);
        } else {
            throw new ValidateException("Proposal status is not VOTE_START : " + Proposal.StatusEnum.find(proposal.getStatus()));
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
     */
    @Override
    public void convertProposalStatus(Proposal proposal) {
        if (proposal.getStatus() == Proposal.StatusEnum.HAS_NOT_STARTED.getValue()) {
            //开始投票的块高
            String voteBeginBn = proposal.getVoteBeginBn();
            //当前块高
            BigInteger curBn = platONClient.platonBlockNumber();
            if (curBn.compareTo(new BigInteger(voteBeginBn)) >= 0) {
                //将投票未开始的状态转换为投票开始的状态
                proposal.setStatus(Proposal.StatusEnum.VOTE_START.getValue());
            }
        }

        if (proposal.getStatus() == Proposal.StatusEnum.VOTE_START.getValue()) {
            //结束投票的块高
            String voteEndBn = proposal.getVoteEndBn();
            //当前块高
            BigInteger curBn = platONClient.platonBlockNumber();
            if (curBn.compareTo(new BigInteger(voteEndBn)) >= 0) {
                //将投票未开始的状态转换为投票开始的状态
                proposal.setStatus(Proposal.StatusEnum.VOTE_END.getValue());
            }
        }


    }

}
