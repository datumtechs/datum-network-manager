package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.common.util.DidUtil;
import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.grpc.client.ProposalClient;
import com.platon.datum.admin.service.AuthorityService;
import com.platon.datum.admin.service.IpfsOpService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2022/7/22 15:31
 * @Version
 * @Desc
 */

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityMapper authorityMapper;
    @Resource
    private ApplyRecordMapper applyRecordMapper;
    @Resource
    private IpfsOpService ipfsOpService;
    @Resource
    private ProposalClient proposalClient;


    /**
     * @param identityId
     * @return
     */
    @Override
    public int getAuthorityCount(String identityId) {
        int i = authorityMapper.selectCount();
        return i;
    }

    /**
     * @param identityId
     * @return
     */
    @Override
    public int getApproveCount(String identityId) {
        int i = applyRecordMapper.selectApproveCount(identityId);
        return i;
    }

    /**
     * @param keyword
     * @return
     */
    @Override
    public List<Authority> getAuthorityList(String keyword) {
        List<Authority> authorityList = authorityMapper.selectList(keyword);
        return authorityList;
    }

    /**
     * @param identityId
     */
    @Override
    public void kickOut(String identityId) {
        String address = DidUtil.didToHexAddress(identityId);
        String proposal = proposalClient.submitProposal(2, "", address, "");
        //TODO 保存提案信息
    }

    /**
     *
     */
    @Override
    public void exit() {
        String observerProxyWalletAddress = OrgCache.getLocalOrgInfo().getObserverProxyWalletAddress();
        String proposal = proposalClient.submitProposal(3, "", observerProxyWalletAddress, "");
        //TODO 保存提案信息
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
     * @param identityId
     * @param ip
     * @param port
     * @param remark       提案备注
     * @param material     提案材料
     * @param materialDesc 提案材料的描述
     *                     {
     *                     "image":"ipfs://bafybeibnsoufr2renqzsh347nrx54wcubt5lgkeivez63xvivplfwhtpym",
     *                     "desc":"图片描述",
     *                     "remark":"公示备注信息"
     *                     }
     */
    @Override
    public void nominate(String identityId, String ip, int port, String remark, String material, String materialDesc) {
        String address = DidUtil.didToHexAddress(identityId);
        Map<String, String> map = new HashMap<>();
        map.put("image", material);
        map.put("desc", materialDesc);
        map.put("remark", remark);
        String url = ipfsOpService.saveJson(map);
        String proposal = proposalClient.submitProposal(1, url, address, ip + port);

        //TODO 保存提案信息
    }
}
