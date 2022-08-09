package com.platon.datum.admin.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.crypto.Credentials;
import com.platon.crypto.Keys;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dao.AttributeDataTokenInventoryMapper;
import com.platon.datum.admin.dao.AttributeDataTokenMapper;
import com.platon.datum.admin.dao.entity.AttributeDataToken;
import com.platon.datum.admin.dao.entity.AttributeDataTokenInventory;
import com.platon.datum.admin.service.AttributeDataTokenInventoryService;
import com.platon.datum.admin.service.entity.TokenUriContent;
import com.platon.datum.admin.service.evm.ERC721Template;
import com.platon.datum.admin.service.web3j.Web3jManager;
import com.platon.protocol.Web3j;
import com.platon.tuples.generated.Tuple3;
import com.platon.tx.gas.ContractGasProvider;
import com.platon.tx.gas.GasProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author liushuyu
 * @Date 2022/7/13 20:10
 * @Version
 * @Desc
 */

@Service
@Slf4j
public class AttributeDataTokenInventoryServiceImpl implements AttributeDataTokenInventoryService {

    @Resource
    private AttributeDataTokenInventoryMapper inventoryMapper;

    @Resource
    private AttributeDataTokenMapper attributeDataTokenMapper;

    @Resource
    private Web3jManager web3jManager;

    private AtomicReference<Web3j> web3jContainer;

    private Credentials credentials;

    @PostConstruct
    public void init() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        web3jContainer = web3jManager.subscribe(this);
        credentials = Credentials.create(Keys.createEcKeyPair());
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @param dataTokenAddress
     * @return
     */
    @Override
    public Page<AttributeDataTokenInventory> page(Integer pageNumber, Integer pageSize, String keyword, String dataTokenAddress) {
        Page<AttributeDataTokenInventory> dataTokenInventoryPage = PageHelper.startPage(pageNumber, pageSize);
        inventoryMapper.selectByDataTokenAddressAndKeyword(dataTokenAddress, keyword);
        return dataTokenInventoryPage;
    }

    /**
     * @param dataTokenAddress
     * @param tokenId
     * @return
     */
    @Override
    public AttributeDataTokenInventory getInventoryByDataTokenAddressAndTokenId(String dataTokenAddress, String tokenId) {
        AttributeDataTokenInventory inventory = inventoryMapper.selectByDataTokenAddressAndTokenId(dataTokenAddress, tokenId);
        return inventory;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void refreshByTokenId(String tokenAddress, String tokenId) {
        ERC721Template erc721Template = load(tokenAddress);
        try {
            AttributeDataTokenInventory inventory = getToken(erc721Template, tokenAddress, new BigInteger(tokenId));
            //存储到数据库中
            inventoryMapper.replace(inventory);
        } catch (Exception e) {
            throw new BizException(Errors.SysException, e);
        }
    }

    //主动刷新5次
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void refresh5(String tokenAddress) {
        ERC721Template erc721Template = load(tokenAddress);
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                refreshTotalAndInventory(tokenAddress, erc721Template);
            } catch (Exception e) {
                throw new BizException(Errors.SysException, e);
            }
        }
    }

    //主动刷新1次
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void refresh(String tokenAddress) {
        ERC721Template erc721Template = load(tokenAddress);
        try {
            refreshTotalAndInventory(tokenAddress, erc721Template);
        } catch (Exception e) {
            throw new BizException(Errors.SysException, e);
        }
    }

    private void refreshTotalAndInventory(String tokenAddress, ERC721Template erc721Template) throws Exception {
        AttributeDataToken attributeDataToken = attributeDataTokenMapper.selectByDataTokenAddress(tokenAddress);
        if (attributeDataToken == null) {
            return;
        }
        BigInteger oldTotal = new BigInteger(attributeDataToken.getTotal());

        //1.通过tokenAddress查询是否生成新的库存
        BigInteger newTotal = erc721Template.totalSupply().send();
        if (newTotal.compareTo(oldTotal) == 0) { //没变化
            return;
        } else { //新增
            //2.如果库存总量有新增，则找出有变化的库存并更新
            addInventory(tokenAddress, newTotal, erc721Template);
            //3.更新dataToken的total
            attributeDataTokenMapper.updateTotalByAddress(newTotal.toString(), tokenAddress);
        }
    }

    private void addInventory(String tokenAddress, BigInteger newTotal, ERC721Template erc721Template) {
        AttributeDataTokenInventory maxTokenIdInventory = inventoryMapper.selectMaxTokenId(tokenAddress);
        BigInteger maxTokenId = new BigInteger(maxTokenIdInventory == null ? "-1" : maxTokenIdInventory.getTokenId());
        for (BigInteger i = maxTokenId.add(BigInteger.ONE); i.compareTo(newTotal) < 0; i = i.add(BigInteger.ONE)) {
            try {
                AttributeDataTokenInventory inventory = getToken(erc721Template, tokenAddress, i);
                //存储到数据库中
                inventoryMapper.replace(inventory);
            } catch (Exception exception) {
                throw new BizException(Errors.SysException, exception);
            }
        }
    }

    private AttributeDataTokenInventory getToken(ERC721Template erc721Template, String tokenAddress, BigInteger tokenId) throws Exception {
        String tokenURI = erc721Template.tokenURI(tokenId).send();
        String name = null;
        String imageUrl = null;
        String desc = null;
        if (JSONUtil.isJson(tokenURI)) {
            TokenUriContent tokenUriContent = JSONUtil.toBean(tokenURI, TokenUriContent.class);
            //获取name
            name = tokenUriContent.getName();
            //image_url
            imageUrl = tokenUriContent.getImage();
            //desc
            desc = tokenUriContent.getDescription();
        }
        //value1是owner，value2是时间戳，value3是是否支持密文
        Tuple3<String, String, Boolean> extInfo = erc721Template.getExtInfo(tokenId).send();
        String owner = extInfo.getValue1();
        String timestamp = extInfo.getValue2();
        boolean isCipher = extInfo.getValue3();
        //usage
        Integer usage = isCipher ? 2 : 1;
        AttributeDataTokenInventory inventory = new AttributeDataTokenInventory(tokenAddress,
                tokenId.toString(),
                name,
                imageUrl,
                desc,
                timestamp,
                usage,
                owner);
        return inventory;
    }

    private BigInteger GAS_LIMIT = BigInteger.valueOf(4104830);
    private BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);
    private GasProvider gasProvider = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);

    private ERC721Template load(String address) {
        Web3j web3j = web3jContainer.get();
        return ERC721Template.load(address, web3j, credentials, gasProvider);
    }
}
