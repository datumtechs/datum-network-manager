package com.platon.datum.admin.service.web3j;

import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dao.SysConfigMapper;
import com.platon.datum.admin.dao.entity.SysConfig;
import com.platon.parameters.NetworkParameters;
import com.platon.protocol.Web3j;
import com.platon.protocol.http.HttpService;
import com.platon.protocol.websocket.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 通过该类可以获取web3j
 * 主要方法 {@link #getWeb3j()} {@link #subscribe(Object)}
 */

@Slf4j
@Component
public class Web3jManager {

    private List<Web3j> web3List = new ArrayList<>();
    private int web3Index = -1;
    private BigInteger maxBlockNumber = BigInteger.ZERO;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    private Map<Object, AtomicReference<Web3j>> subscriptionMap = new ConcurrentHashMap<>();

    private List<String> web3jAddresses;
    private long chainId;
    private String addressPrefix;

    @Resource
    private SysConfigMapper sysConfigMapper;

    @PostConstruct
    public void init() {
        web3jAddresses = Arrays.asList(sysConfigMapper.selectByKey(SysConfig.KeyEnum.RPC_URL_LIST.getKey()).getValue().split(","));
        chainId = Long.parseLong(sysConfigMapper.selectByKey(SysConfig.KeyEnum.CHAIN_ID.getKey()).getValue());
        addressPrefix = sysConfigMapper.selectByKey(SysConfig.KeyEnum.HRP.getKey()).getValue();
        NetworkParameters.init(chainId, addressPrefix);
        // 初始化所有web3j实例
        web3jAddresses.forEach(address ->
                getWeb3j(address).ifPresent(item -> web3List.add(item))
        );
        // 选择最佳节点
        selectBestNode();
    }

    /**
     * web3j实例保活
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void keepAlive() {
        log.info("Web3CheckJob platOnClient begin");
        try {
            this.selectBestNode();
            log.info("Web3CheckJob platOnClient end");
        } catch (Exception e) {
            log.error("Web3CheckJob platOnClient error", e);
        }
    }

    /**
     * 主动获取weg3j连接
     *
     * @return
     */
    public Web3j getWeb3j() {
        try {
            //十秒钟未拿到锁则抛异常
            boolean success = readLock.tryLock(10, TimeUnit.SECONDS);
            if (!success) {
                throw new BizException(Errors.SysException, "获取读锁失败");
            }
            //拿锁成功则直接进入返回web3j连接
            return web3List.get(web3Index);
        } catch (InterruptedException e) {
            throw new BizException(Errors.SysException, e);
        } catch (BizException e) {
            throw e;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 订阅weg3j连接，当web3j链接变更会自动更新，性能会比 {@link #getWeb3j()}好，因为{@link #getWeb3j()}每次需要获取锁
     *
     * @return
     */
    public AtomicReference<Web3j> subscribe(Object observer) {
        AtomicReference<Web3j> web3jContainer = subscriptionMap.get(observer);
        if (web3jContainer == null) {
            web3jContainer = new AtomicReference<>();
            web3jContainer.set(this.getWeb3j());
            subscriptionMap.put(observer, web3jContainer);
        }
        return web3jContainer;
    }

    /**
     * 轮询更新节点
     */
    protected void selectBestNode() {
        BigInteger bestBn = BigInteger.valueOf(-1);
        int bestIndex = -1;

        for (int i = 0; i < web3List.size(); i++) {
            try {
                BigInteger bn = web3List.get(i).platonBlockNumber().send().getBlockNumber();
                if (bn.compareTo(bestBn) > 0) {
                    bestBn = bn;
                    bestIndex = i;
                }
            } catch (Exception e) {
                log.error("check node exception ! ", e);
                Optional<Web3j> optional = getWeb3j(web3jAddresses.get(i));
                if (optional.isPresent()) {
                    Web3j web3j = optional.get();
                    web3List.set(i, web3j);
                    //如果是ws的话，存在连接失效
                    if (web3Index == i) {
                        updateWeb3j(i);
                    }
                }
            }
        }

        if (bestIndex == -1) {
            throw new BizException(Errors.SysException, "无可用节点：bestIndex == -1");
        }

        if (bestIndex != web3Index) {
            log.info("Nodes need switched! usedNode = {} curBn = {}", web3jAddresses.get(bestIndex), bestBn);
            updateWeb3j(bestIndex);
        } else {
            log.info("Nodes no need switched!usedNode = {} curBn = {}", web3jAddresses.get(web3Index), bestBn);
        }

        if (maxBlockNumber.compareTo(bestBn) > 0) {
            log.warn("maxBlockNumber > bestBlockNumber is error");
        } else {
            maxBlockNumber = bestBn;
        }
    }

    private Optional<Web3j> getWeb3j(String address) {
        if (address.startsWith("http")) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log::debug);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
            builder.connectTimeout(10000, TimeUnit.MILLISECONDS);
            builder.readTimeout(60000, TimeUnit.MILLISECONDS);
            OkHttpClient okHttpClient = builder.build();
            return Optional.of(Web3j.build(new HttpService(address, okHttpClient, false)));
        }
        if (address.startsWith("ws")) {
            WebSocketService webSocketService = new WebSocketService(address, false);
            try {
                webSocketService.connect();

            } catch (ConnectException e) {
                log.error("ws connect exception ! ", e);
            }
            return Optional.of(Web3j.build(webSocketService));
        }
        return Optional.empty();
    }

    /**
     * 更新web3j连接索引
     *
     * @param index
     */
    private void updateWeb3j(int index) {
        try {
            boolean success = writeLock.tryLock(15, TimeUnit.SECONDS);
            if (!success) {
                throw new BizException(Errors.SysException, "获取写锁失败");
            }
            web3Index = index;
        } catch (InterruptedException e) {
            throw new BizException(Errors.SysException, e);
        } catch (BizException e) {
            throw e;
        } finally {
            writeLock.unlock();
        }
        //通知订阅的对象，执行回调方法
        onUpdate();
    }

    private void onUpdate() {
        subscriptionMap.forEach((key, value) -> {
            value.set(getWeb3j());
        });
    }

}
