//package com.platon.datum.admin.service.impl;
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.platon.datum.admin.common.exception.BizException;
//import com.platon.datum.admin.common.exception.Errors;
//import com.platon.datum.admin.common.util.DidUtil;
//import com.platon.datum.admin.dao.AuthorityMapper;
//import com.platon.datum.admin.dao.ProposalMapper;
//import com.platon.datum.admin.dao.cache.OrgCache;
//import com.platon.datum.admin.dao.entity.Authority;
//import com.platon.datum.admin.grpc.client.ProposalClient;
//import com.platon.datum.admin.service.IpfsOpService;
//import com.platon.datum.admin.service.ProposalService;
//import com.platon.datum.admin.service.entity.ProposalMaterialContent;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//
///**
// * @Author liushuyu
// * @Date 2022/7/22 16:55
// * @Version
// * @Desc
// */
//
//@Service
//public class ProposalServiceImpl implements ProposalService {
//
//    @Resource
//    private ProposalMapper proposalMapper;
//    @Resource
//    private IpfsOpService ipfsOpService;
//    @Resource
//    private ProposalClient proposalClient;
//    @Resource
//    private AuthorityMapper authorityMapper;
//
//    /**
//     * @param pageNumber
//     * @param pageSize
//     * @param keyword
//     * @return
//     */
//    @Override
//    public Page<Proposal> getProposalList(Integer pageNumber, Integer pageSize, String keyword) {
//        Page<Proposal> proposalPage = PageHelper.startPage(pageNumber, pageSize);
//        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
//        proposalMapper.selectBySponsor(localOrgIdentityId, keyword);
//        return proposalPage;
//    }
//
//    /**
//     * @param id
//     * @return
//     */
//    @Override
//    public Proposal getProposalDetail(int id) {
//        Proposal proposal = proposalMapper.selectById(id);
//        return proposal;
//    }
//
//    /**
//     * @param id
//     */
//    @Override
//    public void revokeProposal(int id) {
//        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
//        Proposal proposal = proposalMapper.selectById(id);
//        if (proposal == null) {
//            throw new BizException(Errors.QueryRecordNotExist,"Proposal not exist");
//        } else if (!proposal.getSponsor().equals(localOrgIdentityId)) {
//            throw new BizException(Errors.SysException,"Proposal not initiated by current organization");
//        }
//        //调用调度服务撤销提案
//        boolean success = proposalClient.withdrawProposal(String.valueOf(id));
//    }
//
//    /**
//     * @param identityId
//     * @return
//     */
//    @Override
//    public int getProposalCount(String identityId) {
//        int count = proposalMapper.selectCountBySponsor(identityId);
//        return count;
//    }
//
//
//    @Override
//    public void nominate(String identityId, String ip, int port, String remark, String material, String materialDesc) {
//        String address = DidUtil.didToHexAddress(identityId);
//        ProposalMaterialContent proposalMaterialContent = new ProposalMaterialContent();
//        proposalMaterialContent.setImage(material);
//        proposalMaterialContent.setDesc(materialDesc);
//        proposalMaterialContent.setRemark(remark);
//        String url = ipfsOpService.saveJson(proposalMaterialContent);
//        String proposal = proposalClient.submitProposal(1, url, address, ip + ":" + port);
//
//        //TODO 保存提案信息
//    }
//
//    /**
//     * @param identityId
//     * @param remark
//     * @param material
//     * @param identityId
//     */
//    @Override
//    public void kickOut(String identityId, String remark, String material, String materialDesc) {
//        Authority authority = authorityMapper.selectByPrimaryKey(identityId);
//        //校验
//        if (authority == null) {
//            throw new BizException(Errors.QueryRecordNotExist, "Authority not exist");
//        }
//        if (OrgCache.getLocalOrgInfo().getIsAuthority() == 0) {
//            throw new BizException(Errors.SysException, "Current org is not authority");
//        }
//
//        //上传文件
//        ProposalMaterialContent proposalMaterialContent = new ProposalMaterialContent();
//        proposalMaterialContent.setImage(material);
//        proposalMaterialContent.setDesc(materialDesc);
//        proposalMaterialContent.setRemark(remark);
//        String url = ipfsOpService.saveJson(proposalMaterialContent);
//
//        //提交提案
//        String address = DidUtil.didToHexAddress(identityId);
//        String proposal = proposalClient.submitProposal(2, url, address, "");
//        //TODO 保存提案信息
//    }
//
//    /**
//     *
//     */
//    @Override
//    public void exit() {
//        String observerProxyWalletAddress = OrgCache.getLocalOrgInfo().getObserverProxyWalletAddress();
//        String proposal = proposalClient.submitProposal(3, "", observerProxyWalletAddress, "");
//        //TODO 保存提案信息
//    }
//
//    /**
//     * @param file
//     */
//    @Override
//    public String upload(MultipartFile file) {
//        String url = ipfsOpService.saveFile(file);
//        return url;
//    }
//}
