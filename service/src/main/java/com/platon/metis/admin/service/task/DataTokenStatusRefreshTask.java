package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.DataTokenMapper;
import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.DataToken;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.metis.admin.service.web3j.Web3jManager;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.core.methods.response.PlatonGetTransactionCount;
import com.platon.protocol.core.methods.response.PlatonTransaction;
import com.platon.protocol.core.methods.response.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description 刷新数据凭证状态定时任务
 */
@Slf4j
@Configuration
public class DataTokenStatusRefreshTask {

    @Resource
    private DataTokenMapper dataTokenMapper;

    @Resource
    private LocalMetaDataMapper localMetaDataMapper;

    @Resource
    private Web3jManager web3jManager;

    private AtomicReference<Web3j> web3jContainer;

    @PostConstruct
    public void init() {
        web3jContainer = web3jManager.getWeb3jContainer(this);
    }

    /**
     * 刷新发布中的数据
     */
    @Scheduled(fixedDelayString = "${DataTokenStatusRefreshTask.fixedDelay}")
    public void refreshPublishingDataToken() {
        log.debug("刷新数据凭证状态定时任务开始>>>");
        //更新发布中的凭证状态
        List<DataToken> dataTokenList = dataTokenMapper.selectListByStatus(DataToken.StatusEnum.PUBLISHING.getStatus());
        dataTokenList.forEach(dataToken -> {
            try {
                ((DataTokenStatusRefreshTask) AopContext.currentProxy()).processPublishingDataToken(dataToken);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        log.info("刷新数据凭证状态定时任务结束|||");
    }

    @Transactional(rollbackFor = {Throwable.class})
    public void processPublishingDataToken(DataToken dataToken) throws IOException {
        if (isExpiredData(dataToken)) {//数据已过期，直接判断为失败
            dataTokenMapper.updateStatus(dataToken.getId(), DataToken.StatusEnum.PUBLISH_FAIL.getStatus());
            localMetaDataMapper.updateStatusById(dataToken.getMetaDataId(), LocalDataFileStatusEnum.TOKEN_FAILED.getStatus());
            return;
        }
        int nonce = dataToken.getPublishNonce();
        String hash = dataToken.getPublishHash();
        String address = dataToken.getOwner();
        Web3j web3j = web3jContainer.get();
        int status = DataToken.StatusEnum.PUBLISHING.getStatus();
        int metaDataStatus = LocalDataFileStatusEnum.TOKEN_RELEASING.getStatus();
        PlatonTransaction platonTransaction = web3j.platonGetTransactionByHash(hash).send();
        if (platonTransaction.getResult() != null) {//已上链
            Transaction transaction = platonTransaction.getResult();
            if (transaction.getFrom().equals(address) || transaction.getNonce().intValue() == nonce) {//凭证发布成功
                status = DataToken.StatusEnum.PUBLISH_SUCCESS.getStatus();
                metaDataStatus = LocalDataFileStatusEnum.TOKEN_RELEASED.getStatus();
            }
        } else if (platonTransaction.getError() != null) {//凭证发布失败
            status = DataToken.StatusEnum.PUBLISH_FAIL.getStatus();
            metaDataStatus = LocalDataFileStatusEnum.TOKEN_FAILED.getStatus();
        } else {//判断nonce是否已经过了,过了则表示凭证发布失败
            PlatonGetTransactionCount count = web3j.platonGetTransactionCount(address, DefaultBlockParameterName.LATEST).send();
            if (count.getTransactionCount().intValue() >= nonce) {
                status = DataToken.StatusEnum.PUBLISH_FAIL.getStatus();
                metaDataStatus = LocalDataFileStatusEnum.TOKEN_FAILED.getStatus();
            }
        }
        dataTokenMapper.updateStatus(dataToken.getId(), status);
        localMetaDataMapper.updateStatusById(dataToken.getMetaDataId(), metaDataStatus);
    }



    /**
     * 刷新定价中的数据
     */
    @Scheduled(fixedDelayString = "${DataTokenStatusRefreshTask.fixedDelay}")
    public void refreshPricingDataToken() {
        log.debug("刷新数据凭证状态定时任务开始>>>");
        //更新定价中的凭证状态
        List<DataToken> dataTokenList = dataTokenMapper.selectListByStatus(DataToken.StatusEnum.PRICING.getStatus());
        dataTokenList.forEach(dataToken -> {
            if (isExpiredData(dataToken)) {//数据已过期，直接判断为失败
                dataTokenMapper.updateStatus(dataToken.getId(), DataToken.StatusEnum.PRICE_FAIL.getStatus());
                return;
            }
            int nonce = dataToken.getPublishNonce();
            String hash = dataToken.getPublishHash();
            String address = dataToken.getOwner();
            Web3j web3j = web3jContainer.get();
            int status = DataToken.StatusEnum.PRICING.getStatus();
            try {
                PlatonTransaction platonTransaction = web3j.platonGetTransactionByHash(hash).send();
                if (platonTransaction.getResult() != null) {//已上链
                    Transaction transaction = platonTransaction.getResult();
                    if (transaction.getFrom().equals(address) || transaction.getNonce().intValue() == nonce) {//凭证定价成功
                        status = DataToken.StatusEnum.PRICE_SUCCESS.getStatus();
                    }
                } else if (platonTransaction.getError() != null) {//凭证定价失败
                    status = DataToken.StatusEnum.PRICE_FAIL.getStatus();
                } else {//判断nonce是否已经过了,过了则表示凭证定价失败
                    PlatonGetTransactionCount count = web3j.platonGetTransactionCount(address, DefaultBlockParameterName.LATEST).send();
                    if (count.getTransactionCount().intValue() >= nonce) {
                        status = DataToken.StatusEnum.PRICE_FAIL.getStatus();
                    }
                }
                dataTokenMapper.updateStatus(dataToken.getId(), status);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        log.info("刷新数据凭证状态定时任务结束|||");
    }

    private boolean isExpiredData(DataToken dataToken) {
        LocalDateTime updateTime = dataToken.getRecUpdateTime();
        long timeStamp = updateTime.plusHours(12).toInstant(ZoneOffset.UTC).toEpochMilli();
        if (timeStamp < new Date().getTime()) {
            return true;
        }
        return false;
    }
}
