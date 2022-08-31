package com.platon.datum.admin.service.task;

import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.service.web3j.Web3jManager;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.methods.response.PlatonGetTransactionReceipt;
import com.platon.protocol.core.methods.response.TransactionReceipt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @author liushuyu
 * @Description 刷新委员会成员对VC的操作记录状态
 */

@Slf4j
@Configuration
public class ApplyRecordStatusTask {

    @Resource
    private ApplyRecordMapper applyRecordMapper;

    @Resource
    private Web3jManager web3jManager;

    private AtomicReference<Web3j> web3jContainer;

    @Resource
    private AuthorityMapper authorityMapper;

    @PostConstruct
    public void init() {
        web3jContainer = web3jManager.subscribe(this);
    }

    @Scheduled(fixedDelayString = "${ApplyRecordStatusTask.fixedDelay}")
    public void refreshTobeEffectiveApplyRecord() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        //1.如果本组织不是委员会成员，则无需关心该任务
        Authority authority = authorityMapper.selectByPrimaryKey(OrgCache.getLocalOrgIdentityId());
        if (authority == null) {
            return;
        }
        log.debug("刷新委员会成员对VC的操作记录状态定时任务开始>>>");
        List<ApplyRecord> tobeEffectiveApplyRecordList = applyRecordMapper.selectByApproveOrgAndTobeEffective(OrgCache.getLocalOrgIdentityId());
        tobeEffectiveApplyRecordList.forEach(applyRecord -> {
            try {
                ((ApplyRecordStatusTask) AopContext.currentProxy()).process(applyRecord);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        });
        log.debug("刷新委员会成员对VC的操作记录状态定时任务结束|||");
    }

    @Transactional(rollbackFor = {Throwable.class})
    public void process(ApplyRecord record) throws Exception {
        int status = ApplyRecord.StatusEnum.TO_BE_EFFECTIVE.getStatus();
        //判断是否过期
        if (isExpiredData(record)) {
            status = ApplyRecord.StatusEnum.INVALID.getStatus();
        } else {
            String hash = record.getTxHash();
            Web3j web3j = web3jContainer.get();
            //1.判断交易上链结果
            PlatonGetTransactionReceipt transactionReceiptResp = web3j.platonGetTransactionReceipt(hash).send();
            //已上链
            if (transactionReceiptResp.getResult() != null) {
                TransactionReceipt transactionReceipt = transactionReceiptResp.getResult();
                //交易状态
                boolean isOK = transactionReceipt.isStatusOK();
                //交易成功
                if (isOK) {
                    status = ApplyRecord.StatusEnum.VALID.getStatus();
                } else {
                    status = ApplyRecord.StatusEnum.INVALID.getStatus();
                }
            }
        }
        record.setStatus(status);
        //2.结果入库
        applyRecordMapper.updateByPrimaryKeySelective(record);
    }

    private boolean isExpiredData(ApplyRecord record) {
        LocalDateTime endTime = record.getEndTime();
        long timeStamp = LocalDateTimeUtil.getTimestamp(endTime.plusHours(24));
        if (timeStamp < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
