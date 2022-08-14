package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.Proposal;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author liushuyu
 * @Date 2022/7/22 15:24
 * @Version
 * @Desc
 */
public interface ProposalService {
    Page<Proposal> getMyProposalList(Integer pageNumber, Integer pageSize, String keyword);

    Proposal getProposalDetail(String id);

    void revokeProposal(String id);

    int getProposalCount(String identityId);

    /**
     * 发起加人提案
     *
     * @param identityId
     * @param ip
     * @param port
     * @param remark
     * @param material
     * @param materialDesc
     */
    void nominate(String identityId, String ip, int port, String remark, String material, String materialDesc);

    /**
     * 发起踢人提案
     *
     * @param identityId
     * @param remark
     * @param material
     * @param materialDesc
     */
    void kickOut(String identityId, String remark, String material, String materialDesc);

    /**
     * 发起主动退出提案
     */
    void exit();

    /**
     * 投票
     */
    void vote(String proposalId);

    String upload(MultipartFile file);

    void convertProposalStatus(Proposal proposal);
}
