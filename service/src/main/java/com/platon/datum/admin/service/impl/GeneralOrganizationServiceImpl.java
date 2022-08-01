package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.grpc.client.DidClient;
import com.platon.datum.admin.service.GeneralOrganizationService;
import com.platon.datum.admin.service.IpfsOpService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author liushuyu
 * @Date 2022/7/22 12:02
 * @Version
 * @Desc
 */


@Service
public class GeneralOrganizationServiceImpl implements GeneralOrganizationService {

    @Resource
    private ApplyRecordMapper applyRecordMapper;
    @Resource
    private IpfsOpService ipfsOpService;
    @Resource
    private DidClient didClient;

    /**
     * @return
     */
    @Override
    public int getCredentialsCount() {
        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
        int i = applyRecordMapper.selectPassCount(localOrgIdentityId);
        return i;
    }

    /**
     * @return
     */
    @Override
    public int getApplyCount() {
        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
        int i = applyRecordMapper.selectApplyCount(localOrgIdentityId);
        return i;
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<ApplyRecord> getApplyList(Integer pageNumber, Integer pageSize) {
        Page<ApplyRecord> applyRecords = PageHelper.startPage(pageNumber, pageSize);
        String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
        applyRecordMapper.selectListByApplyOrg(localOrgIdentityId);
        return applyRecords;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ApplyRecord getApplyDetail(Integer id) {
        ApplyRecord applyRecord = applyRecordMapper.selectById(id);
        return applyRecord;
    }

    /**
     * @param id
     * @param response
     * TODO
     */
    @Override
    public void download(Integer id, HttpServletResponse response) {
        ApplyRecord applyRecord = applyRecordMapper.selectById(id);

    }

    /**
     * @param file
     * @return
     */
    @Override
    public String uploadMaterial(MultipartFile file) {
        String ipfsUrl = ipfsOpService.saveFile(file);
        return ipfsUrl;
    }

    /**
     * @param approve
     * @param remark
     * @param material
     * @param desc
     */
    @Override
    public void apply(String approve, String remark, String material, String desc) {
        String observerProxyWalletAddress = OrgCache.getLocalOrgInfo().getObserverProxyWalletAddress();
        String txHash = didClient.createVC("", 0, "", 1, "");
        //TODO 保存提案信息
    }
}
