package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.AttributeDataTokenInventory;

/**
 * @Author liushuyu
 * @Date 2022/7/13 15:53
 * @Version
 * @Desc
 */
public interface AttributeDataTokenInventoryService {

    Page<AttributeDataTokenInventory> page(Integer pageNumber, Integer pageSize, String keyword, String dataTokenAddress);

    AttributeDataTokenInventory getInventoryByDataTokenAddressAndTokenId(String dataTokenAddress, String tokenId);

    void refreshByTokenId(String tokenAddress, String tokenId);

    //主动刷新5次
    void refresh5(String tokenAddress);

    //主动刷新1次
    void refresh(String tokenAddress);
}
