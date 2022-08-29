package com.platon.datum.admin.service.task;

import cn.hutool.json.JSONUtil;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.grpc.client.DidClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author liushuyu
 * @Description 刷新普通成员的VC状态
 */

@Slf4j
@Configuration
public class GeneralOrganizationApplyRecordStatusTask {

    @Resource
    private ApplyRecordMapper applyRecordMapper;

    @Resource
    private AuthorityMapper authorityMapper;

    @Resource
    private DidClient didClient;

    @Scheduled(fixedDelayString = "${GeneralOrganizationApplyRecordStatusTask.fixedDelay}")
    public void refresh() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        //1.如果本组织是委员会成员，则无需关心该任务
        Authority authority = authorityMapper.selectByPrimaryKey(OrgCache.getLocalOrgIdentityId());
        if (authority != null) {
            return;
        }
        log.debug("刷新VC状态开始>>>");
        List<ApplyRecord> applyRecordList = applyRecordMapper.selectByProgress(ApplyRecord.ProgressEnum.APPLYING.getStatus());
        applyRecordList.forEach(applyRecord -> {
            try {
                String applyOrg = applyRecord.getApplyOrg();
                if(!applyOrg.equalsIgnoreCase(OrgCache.getLocalOrgIdentityId())){
                    return;
                }
                refreshApplyRecordStatus(applyRecord);
            } catch (Throwable throwable) {
                log.error("刷新VC状态失败",throwable);
            }
        });
        log.debug("刷新VC状态结束|||");
    }

    private void refreshApplyRecordStatus(ApplyRecord applyRecord) {
        //1.校验
        Authority authority = authorityMapper.selectByPrimaryKey(applyRecord.getApproveOrg());
        if (authority == null) {
            throw new BizException(Errors.QueryRecordNotExist, "Approve is not exist");
        }

        Integer progress = applyRecord.getProgress();
        //如果审批同意，则下载vc
        if (progress == ApplyRecord.ProgressEnum.APPLYING.getStatus()) {
            //调用下载接口
            String applyRecordJson = didClient.downloadVCLocal(applyRecord.getApproveOrg(),
                    authority.getUrl(),
                    applyRecord.getApplyOrg());
            ApplyRecord issuerApplyRecord = JSONUtil.toBean(applyRecordJson, ApplyRecord.class);
            //更新申请记录
            ApplyRecord newApplyRecord = updateApplyRecord(applyRecord, issuerApplyRecord);
            applyRecordMapper.updateByPrimaryKeySelective(newApplyRecord);
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
}
