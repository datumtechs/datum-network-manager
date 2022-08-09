package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class AttributeDataTokenInventory extends BaseDomain {
    /**
    * 合约地址
    */
    @ApiModelProperty("合约地址")
    private String dataTokenAddress;

    /**
    * 库存ID
    */
    @ApiModelProperty("库存ID")
    private String tokenId;

    /**
    * 名称
    */
    @ApiModelProperty("名称")
    private String name;

    /**
    * 图片路劲
    */
    @ApiModelProperty("图片路劲")
    private String imageUrl;

    /**
    * 描述
    */
    @ApiModelProperty("描述")
    private String desc;

    /**
    * 库存有效期的结束时间
    */
    @ApiModelProperty("库存有效期的结束时间")
    private String endTime;

    /**
    * 用法：1-明文，2-密文
    */
    @ApiModelProperty("用法：1-明文，2-密文")
    private Integer usage;

    /**
     * 该库存的owner
     */
    @ApiModelProperty("该库存的owner")
    private String owner;

    /**
     * 元数据列表id
     */
    private Integer metaDataDbId;
}