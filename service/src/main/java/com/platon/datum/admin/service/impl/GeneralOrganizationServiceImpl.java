package com.platon.datum.admin.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.ExportFileUtil;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.grpc.client.AuthClient;
import com.platon.datum.admin.grpc.client.DidClient;
import com.platon.datum.admin.service.GeneralOrganizationService;
import com.platon.datum.admin.service.IpfsOpService;
import com.platon.datum.admin.service.OrgService;
import com.platon.datum.admin.service.entity.Pct1000;
import com.platon.datum.admin.service.entity.VcMaterialContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
    @Resource
    private AuthorityMapper authorityMapper;
    @Value("${vc.pctId:1000}")
    private Integer pctId;
    @Resource
    private OrgService orgService;
    @Resource
    private AuthClient authClient;

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
     * @return
     */
    @Override
    public ApplyRecord getCurrentUsingVc() {
        //查询出当前组织已生效且已使用的证书
        List<ApplyRecord> applyRecordList = applyRecordMapper.selectByUsedAndValidVc(OrgCache.getLocalOrgIdentityId());
        return applyRecordList.isEmpty() ? null : applyRecordList.get(0);
    }

    /**
     * @return
     */
    @Override
    public boolean currentOrgCanTrusted() {
        ApplyRecord currentUsingVc = getCurrentUsingVc();
        return currentUsingVc == null ? false : true;
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
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void download(Integer id, HttpServletResponse response) {
        ApplyRecord applyRecord = applyRecordMapper.selectById(id);
        //1.校验
        if (applyRecord == null) {
            throw new BizException(Errors.QueryRecordNotExist, "Apply record is not exist");
        }

        Authority authority = authorityMapper.selectByPrimaryKey(applyRecord.getApproveOrg());
        if (authority == null) {
            throw new BizException(Errors.AuthorityNotExist, "Approve is not exist");
        }

        Integer progress = applyRecord.getProgress();
        //如果审批同意，则下载vc
        if (progress == ApplyRecord.ProgressEnum.AGREE.getStatus()) {
            downloadVcFile(applyRecord, response);
        } else if (progress == ApplyRecord.ProgressEnum.APPLYING.getStatus()) {
            //调用下载接口
            String applyRecordJson = didClient.downloadVCLocal(applyRecord.getApproveOrg(),
                    authority.getUrl(),
                    applyRecord.getApplyOrg());
            ApplyRecord issuerApplyRecord = JSONUtil.toBean(applyRecordJson, ApplyRecord.class);
            Integer newProgress = issuerApplyRecord.getProgress();
            if (newProgress == ApplyRecord.ProgressEnum.APPLYING.getStatus()) {
                throw new BizException(Errors.ApplyRecordIsApplying);
            }
            //更新申请记录
            ApplyRecord newApplyRecord = updateApplyRecord(applyRecord, issuerApplyRecord);
            applyRecordMapper.updateByPrimaryKeySelective(newApplyRecord);
            //如果审批同意，则下载vc
            if (newProgress == ApplyRecord.ProgressEnum.AGREE.getStatus()) {
                downloadVcFile(newApplyRecord, response);
            } else {
                throw new BizException(Errors.ApplyRecordIsRejected);
            }
        } else {
            throw new BizException(Errors.ApplyRecordIsRejected);
        }
    }

    private void downloadVcFile(ApplyRecord applyRecord, HttpServletResponse response) {
        //下载成功
        String vcName = applyRecord.getApplyOrg() + "_"
                + applyRecord.getApproveOrg() + "_"
                + LocalDateTimeUtil.getTimestamp(applyRecord.getStartTime()) + "_"
                + LocalDateTimeUtil.getTimestamp(applyRecord.getEndTime()) + "_"
                + applyRecord.getExpirationDate();
        String vc = applyRecord.getVc();
        try {
            ExportFileUtil.exportJson(vcName, vc.getBytes(StandardCharsets.UTF_8), response);
        } catch (IOException e) {
            throw new BizException(Errors.ExportFileError, e);
        }
    }

    private ApplyRecord updateApplyRecord(ApplyRecord applyRecord, ApplyRecord issuerApplyRecord) {
        Integer progress = issuerApplyRecord.getProgress();
        applyRecord.setProgress(progress);
        applyRecord.setStatus(issuerApplyRecord.getStatus());
        applyRecord.setApproveRemark(issuerApplyRecord.getApproveRemark());
        applyRecord.setEndTime(issuerApplyRecord.getEndTime());
        applyRecord.setVc(issuerApplyRecord.getVc());
        applyRecord.setExpirationDate(issuerApplyRecord.getExpirationDate());
        return applyRecord;
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
     * @param approve  审批组织
     * @param remark   申请的备注
     * @param material 申请材料的ipfsUrl
     * @param desc     申请材料的描述
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void apply(String approve, String remark, String material, String desc) {

        Authority authority = authorityMapper.selectByPrimaryKey(approve);
        if (authority == null) {
            throw new BizException(Errors.AuthorityNotExist, "Approve is not authority");
        }

        Org applyOrg = OrgCache.getLocalOrgInfo();

        /**
         * 判断是否该申请的审批方是否已经有一个生效中的申请存在，如果存在则不允许申请了
         */
        List<ApplyRecord> applyRecord1 = applyRecordMapper.selectByApplyOrgAndApproveOrgAndStatus(applyOrg.getIdentityId(),
                approve,
                ApplyRecord.StatusEnum.VALID.getStatus());
        if (!applyRecord1.isEmpty()) {
            throw new BizException(Errors.VcAlreadyExists, "The vc already exists");
        }
        /**
         * 判断是否该申请的审批方是否已经有一个审批中的申请存在，如果存在则不允许申请了
         */
        List<ApplyRecord> applyRecord2 = applyRecordMapper.selectByApplyOrgAndApproveOrgAndStatus(applyOrg.getIdentityId(),
                approve,
                ApplyRecord.StatusEnum.TO_BE_EFFECTIVE.getStatus());
        if (!applyRecord2.isEmpty()) {
            throw new BizException(Errors.ApplyingVcAlreadyExists, "A applying vc already exists");
        }

        /**
         * 将材料描述和材料一起上传ipfs
         */
        VcMaterialContent vcMaterialContent = new VcMaterialContent();
        vcMaterialContent.setImage(material);
        vcMaterialContent.setDesc(desc);
        String ipfsUrl = ipfsOpService.saveJson(vcMaterialContent);

        //生成claim
        Pct1000 pct1000 = new Pct1000();
        pct1000.setNodeId(applyOrg.getIdentityId());
        pct1000.setNodeName(applyOrg.getName());
        pct1000.setUrl(ipfsUrl);

        ApplyRecord applicantRecord = new ApplyRecord();
        applicantRecord.setPctId(pctId);
        applicantRecord.setApplyOrg(applyOrg.getIdentityId());
        applicantRecord.setApproveOrg(approve);
        applicantRecord.setContext("");
        applicantRecord.setClaim(JSONUtil.toJsonStr(pct1000));
        applicantRecord.setStartTime(LocalDateTimeUtil.now());
        applicantRecord.setApplyRemark(remark);
        applicantRecord.setMaterial(material);
        applicantRecord.setMaterialDesc(desc);
        applicantRecord.setProgress(ApplyRecord.ProgressEnum.APPLYING.getStatus());
        applicantRecord.setStatus(ApplyRecord.StatusEnum.TO_BE_EFFECTIVE.getStatus());

        didClient.applyVCLocal(applicantRecord, authority.getUrl());
        //保存提案信息
        int i = applyRecordMapper.insertSelective(applicantRecord);
        if (i <= 0) {
            throw new BizException(Errors.InsertSqlFailed, "Insert apply record failed");
        }
    }

    /**
     * @param id
     */
    @Override
    public void use(Integer id) {
        ApplyRecord applyRecord = applyRecordMapper.selectById(id);
        //1.校验
        if (applyRecord == null) {
            throw new BizException(Errors.QueryRecordNotExist, "Apply record is not exist");
        }

        //查询出当前组织是否有已生效且已使用的证书
        List<ApplyRecord> applyRecordList = applyRecordMapper.selectByUsedAndValidVc(applyRecord.getApplyOrg());
        if (!applyRecordList.isEmpty()) {
            throw new BizException(Errors.UsedVcIsExist);
        }

        Integer status = applyRecord.getStatus();
        //如果审批同意，则下载vc
        if (status == ApplyRecord.StatusEnum.INVALID.getStatus()) {
            throw new BizException(Errors.VcIsInvalid);
        } else if (status == ApplyRecord.StatusEnum.VALID.getStatus()) {
            applyRecord.setUsed(1);
            applyRecordMapper.updateByPrimaryKeySelective(applyRecord);
        } else {
            throw new BizException(Errors.ApplyRecordIsApplying);
        }
        authClient.updateIdentityCredential(applyRecord.getVc());
        orgService.updateCredential(applyRecord.getVc());
    }
}
