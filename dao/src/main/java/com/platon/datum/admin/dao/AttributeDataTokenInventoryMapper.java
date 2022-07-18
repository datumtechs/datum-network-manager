package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.AttributeDataTokenInventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/7/12 18:26
 * @Version
 * @Desc ******************************
 */
public interface AttributeDataTokenInventoryMapper {
    int insert(AttributeDataTokenInventory record);

    List<AttributeDataTokenInventory> selectByDataTokenAddressAndKeyword(@Param("dataTokenAddress") String dataTokenAddress, @Param("keyword") String keyword);

    AttributeDataTokenInventory selectByDataTokenAddressAndTokenId(@Param("dataTokenAddress") String dataTokenAddress, @Param("tokenId") String tokenId);

    AttributeDataTokenInventory selectMaxTokenId(@Param("tokenAddress") String tokenAddress);

    void replace(AttributeDataTokenInventory inventory);

}