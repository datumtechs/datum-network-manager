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
    Page<Proposal> getProposalList(Integer pageNumber, Integer pageSize, String keyword);

    Proposal getProposalDetail(int id);

    void revokeProposal(int id);

    int getProposalCount(String identityId);

    void nominate(String identityId, String ip, int port, String remark, String material, String materialDesc);

    void kickOut(String identityId, String remark, String material, String materialDesc);

    void exit();

    String upload(MultipartFile file);
}
