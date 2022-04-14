package com.platon.metis.admin.service.task;

import cn.hutool.core.util.StrUtil;
import com.platon.bech32.Bech32;
import com.platon.metis.admin.dao.DataTokenMapper;
import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.DataToken;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.metis.admin.service.web3j.Web3jManager;
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
        log.debug("刷新数据凭证[发布状态]定时任务开始>>>");
        //更新发布中的凭证状态
        List<DataToken> dataTokenList = dataTokenMapper.selectListByStatus(DataToken.StatusEnum.PUBLISHING.getStatus());
        dataTokenList.forEach(dataToken -> {
            try {
                ((DataTokenStatusRefreshTask) AopContext.currentProxy()).processPublishingDataToken(dataToken);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        log.info("刷新数据凭证[发布状态]定时任务结束|||");
    }

    @Transactional(rollbackFor = {Throwable.class})
    public void processPublishingDataToken(DataToken dataToken) throws IOException {
        if (isExpiredData(dataToken)) {//1.数据已过期，直接判断为失败
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

        //2.判断交易上链结果
        PlatonGetTransactionReceipt transactionReceiptResp = web3j.platonGetTransactionReceipt(hash).send();
        if (transactionReceiptResp.getResult() != null) {//已上链
            TransactionReceipt transactionReceipt = transactionReceiptResp.getResult();
            //交易状态
            boolean isOK = transactionReceipt.isStatusOK();
            //交易的发起人
            String hexFrom = Bech32.addressDecodeHex(transactionReceipt.getFrom());
            //交易的nonce
            int transactionNonce = web3j.platonGetTransactionByHash(hash).send().getResult().getNonce().intValue();
            log.debug("isOK:{},hexFrom:{},transactionNonce:{}", isOK, hexFrom, transactionNonce);
            if (isOK && address.equals(hexFrom) && transactionNonce == nonce) {//凭证发布成功
                status = DataToken.StatusEnum.PUBLISH_SUCCESS.getStatus();
                metaDataStatus = LocalDataFileStatusEnum.TOKEN_RELEASED.getStatus();
                //发布成功，获取token地址
                String data = transactionReceipt.getLogs().get(0).getData();
                String dataTokenAddress = StrUtil.sub(data, data.length() - 40, data.length() + 1);
                dataTokenMapper.updateTokenAddress(dataToken.getId(), dataTokenAddress);
            } else {
                status = DataToken.StatusEnum.PUBLISH_FAIL.getStatus();
                metaDataStatus = LocalDataFileStatusEnum.TOKEN_FAILED.getStatus();
            }
        }

        //4.结果入库
        if (status == DataToken.StatusEnum.PUBLISH_SUCCESS.getStatus()
                || status == DataToken.StatusEnum.PUBLISH_FAIL.getStatus()) {
            dataTokenMapper.updateStatus(dataToken.getId(), status);
            localMetaDataMapper.updateStatusById(dataToken.getMetaDataId(), metaDataStatus);
        }
    }


    /**
     * 刷新定价中的数据
     */
    @Scheduled(fixedDelayString = "${DataTokenStatusRefreshTask.fixedDelay}")
    public void refreshPricingDataToken() {
        log.debug("刷新数据凭证[定价状态]定时任务开始>>>");
        //更新定价中的凭证状态
        List<DataToken> dataTokenList = dataTokenMapper.selectListByStatus(DataToken.StatusEnum.PRICING.getStatus());
        dataTokenList.forEach(dataToken -> {
            if (isExpiredData(dataToken)) {//1.数据已过期，直接判断为失败
                dataTokenMapper.updateStatus(dataToken.getId(), DataToken.StatusEnum.PRICE_FAIL.getStatus());
                return;
            }
            int nonce = dataToken.getPublishNonce();
            String hash = dataToken.getPublishHash();
            String address = dataToken.getOwner();
            Web3j web3j = web3jContainer.get();
            int status = DataToken.StatusEnum.PRICING.getStatus();
            try {
                //2.判断交易上链结果
                PlatonGetTransactionReceipt transactionReceiptResp = web3j.platonGetTransactionReceipt(hash).send();
                if (transactionReceiptResp.getResult() != null) {//已上链
                    TransactionReceipt transactionReceipt = transactionReceiptResp.getResult();
                    //交易状态
                    boolean isOK = transactionReceipt.isStatusOK();
                    //交易的发起人
                    String hexFrom = Bech32.addressDecodeHex(transactionReceipt.getFrom());
                    //交易的nonce
                    int transactionNonce = web3j.platonGetTransactionByHash(hash).send().getResult().getNonce().intValue();
                    log.debug("isOK:{},hexFrom:{},transactionNonce:{}", isOK, hexFrom, transactionNonce);
                    if (isOK && address.equals(hexFrom) && transactionNonce == nonce) {//凭证定价成功
                        status = DataToken.StatusEnum.PRICE_SUCCESS.getStatus();
                    } else {
                        status = DataToken.StatusEnum.PRICE_FAIL.getStatus();
                    }
                }

                //4.结果入库
                if (status == DataToken.StatusEnum.PRICE_SUCCESS.getStatus() || status == DataToken.StatusEnum.PRICE_FAIL.getStatus()) {
                    dataTokenMapper.updateStatus(dataToken.getId(), status);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        log.info("刷新数据凭证[定价状态]定时任务结束|||");
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
