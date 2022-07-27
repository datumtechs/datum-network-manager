package com.platon.datum.admin.dao.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author liushuyu
 * @Date 2022/7/12 18:26
 * @Version 
 * @Desc 
 *******************************
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDataTokenInventory {
    /**
    * 合约地址
    */
    private String dataTokenAddress;

    /**
    * 库存ID
    */
    private String tokenId;

    /**
    * 名称
    */
    private String name;

    /**
    * 图片路劲
    */
    private String imageUrl;

    /**
    * 描述
    */
    private String desc;

    /**
    * 库存有效期的结束时间
    */
    private LocalDateTime endTime;

    /**
    * 用法：1-明文，2-密文
    */
    private Integer usage;

    /**
     * 该库存的owner
     */
    private String owner;
}