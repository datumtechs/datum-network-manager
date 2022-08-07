package com.platon.datum.admin.service.task;

import cn.hutool.core.util.StrUtil;
import com.platon.abi.solidity.EventEncoder;
import com.platon.abi.solidity.TypeReference;
import com.platon.abi.solidity.datatypes.Address;
import com.platon.abi.solidity.datatypes.Event;
import com.platon.abi.solidity.datatypes.Utf8String;
import com.platon.bech32.Bech32;
import com.platon.datum.admin.common.util.AddressChangeUtil;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.DataTokenMapper;
import com.platon.datum.admin.dao.SysConfigMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.DataToken;
import com.platon.datum.admin.dao.entity.SysConfig;
import com.platon.datum.admin.service.web3j.Web3jManager;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.methods.response.Log;
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
import java.util.Arrays;
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
    private Web3jManager web3jManager;

    private AtomicReference<Web3j> web3jContainer;

    @Resource
    private SysConfigMapper sysConfigMapper;

    @PostConstruct
    public void init() {
        web3jContainer = web3jManager.subscribe(this);
    }

    /**
     * 刷新发布中的数据
     */
    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(fixedDelayString = "${DataTokenStatusRefreshTask.fixedDelay}")
    public void refreshPublishingDataToken() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        log.debug("刷新数据凭证[发布状态]定时任务开始>>>");
        //更新发布中的凭证状态
        List<DataToken> dataTokenList = dataTokenMapper.selectListByStatus(DataToken.StatusEnum.PUBLISHING.getStatus());
        dataTokenList.forEach(dataToken -> {
            try {
                ((DataTokenStatusRefreshTask) AopContext.currentProxy()).processPublishingDataToken(dataToken);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        });
        log.debug("刷新数据凭证[发布状态]定时任务结束|||");
    }

    @Transactional(rollbackFor = {Throwable.class})
    public void processPublishingDataToken(DataToken dataToken) throws IOException {
        if (isExpiredData(dataToken)) {//1.数据已过期，直接判断为失败
            dataTokenMapper.updateStatus(dataToken.getId(), DataToken.StatusEnum.PUBLISH_FAIL.getStatus());
            return;
        }
        int nonce = dataToken.getPublishNonce();
        String hash = dataToken.getPublishHash();
        String address = dataToken.getOwner();
        Web3j web3j = web3jContainer.get();
        int status = DataToken.StatusEnum.PUBLISHING.getStatus();

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
                //发布成功，获取token地址
                String dataTokenAddress = getTokenAddress(transactionReceipt);
                dataTokenMapper.updateTokenAddress(dataToken.getId(), "0x" + dataTokenAddress);
            } else {
                status = DataToken.StatusEnum.PUBLISH_FAIL.getStatus();
            }
        }

        //4.结果入库
        if (status == DataToken.StatusEnum.PUBLISH_SUCCESS.getStatus()
                || status == DataToken.StatusEnum.PUBLISH_FAIL.getStatus()) {
            dataTokenMapper.updateStatus(dataToken.getId(), status);
        }
    }

    private String getTokenAddress(TransactionReceipt transactionReceipt) {
        SysConfig sysConfig = sysConfigMapper.selectByKey(SysConfig.KeyEnum.DATA_TOKEN_FACTORY_ADDRESS.getKey());
        String dataTokenFactory = sysConfig.getValue();
        Event tokenCreatedEvent = new Event("TokenCreated",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
                }, new TypeReference<Address>(true) {
                }, new TypeReference<Utf8String>(true) {
                }));
        String eventSign = EventEncoder.encode(tokenCreatedEvent);
        for (int i = 0; i < transactionReceipt.getLogs().size(); i++) {
            Log logs = transactionReceipt.getLogs().get(i);
            if (AddressChangeUtil.convert0xAddress(logs.getAddress()).equals(dataTokenFactory.toLowerCase())) {
                List<String> topics = logs.getTopics();
                //事件方法签名等于TokenCreated(token, tokenTemplate, name)
                String eventSign1 = topics.get(0);
                if (eventSign.equals(eventSign1)) {
                    String address = topics.get(1);
                    String dataTokenAddress = StrUtil.sub(address, address.length() - 40, address.length() + 1);
                    return dataTokenAddress;
                }
            }
        }
        return null;
    }

    /**
     * 刷新定价中的数据
     */
    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(fixedDelayString = "${DataTokenStatusRefreshTask.fixedDelay}")
    public void refreshPricingDataToken() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        log.debug("刷新数据凭证[定价状态]定时任务开始>>>");
        //更新定价中的凭证状态
        List<DataToken> dataTokenList = dataTokenMapper.selectListByStatus(DataToken.StatusEnum.PRICING.getStatus());
        dataTokenList.forEach(dataToken -> {
            if (isExpiredData(dataToken)) {//1.数据已过期，直接判断为失败
                dataTokenMapper.updateStatus(dataToken.getId(), DataToken.StatusEnum.PRICE_FAIL.getStatus());
                return;
            }
            int nonce = dataToken.getUpNonce();
            String hash = dataToken.getUpHash();
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
        long timeStamp = LocalDateTimeUtil.getTimestamp(updateTime.plusHours(12));
        if (timeStamp < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    //TODO 12个小时候自动失败
    public void refreshPublishFail() {

    }
}
