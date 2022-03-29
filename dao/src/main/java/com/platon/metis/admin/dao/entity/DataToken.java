package com.platon.metis.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author liushuyu
 * @Date 2022/3/16 11:52
 * @Version
 * @Desc 地址，名称，符号，总发布量，价值证明描述，用户钱包地址
 */


@Getter
@Setter
@ToString
@ApiModel("数据凭证")
public class DataToken {

    //id
    @ApiModelProperty("id")
    private int id;

    //地址
    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("名称")
    //名称
    private String name;

    @ApiModelProperty("符号")
    //符号
    private String symbol;

    @ApiModelProperty("总发行量")
    //总发行量
    private String total;

    @ApiModelProperty("精度")
    //精度
    private Integer decimal;

    @ApiModelProperty("价值证明描述")
    //价值证明描述
    private String desc;

    @ApiModelProperty("拥有者钱包地址")
    //拥有者钱包地址
    private String owner;

    @ApiModelProperty("代理钱包地址")
    //代理钱包地址
    private String agent;

    @ApiModelProperty("对应的metaDataId")
    //对应的metaDataId
    private Integer metaDataId;

    @ApiModelProperty("持有人数量")
    //持有人数量
    private long holder;

    @ApiModelProperty("定价状态：0-未定价，1-定价中，2-定价失败，3-定价成功")
    /**
     * 定价状态：0-未定价，1-定价中，2-定价失败，3-定价成功
     * {@link PricingStatusEnum}
     */
    private int pricingStatus;

    /**
     * 发布状态：0-发布中，1-发布失败，2-发布成功
     * {@link PublishStatusEnum}
     */
    @ApiModelProperty("发布状态：0-发布中，1-发布失败，2-发布成功")
    private int publishStatus;

    //发布发布中，发布失败，发布成功
    @ApiModelProperty("发布合约的交易hash")
    private String publishHash;

    //上架中，上架失败，上架成功
    @ApiModelProperty("上架dex的交易hash")
    private String upHash;

    //创建时间
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    //更新时间
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @Getter
    public enum PricingStatusEnum {
        UNPRICED(0,"未定价"),
        PRICING(1,"定价中"),
        PRICE_FAIL(2,"定价失败"),
        PRICE_SUCCESS(3,"定价成功");

        PricingStatusEnum(int status,String desc){
            this.status = status;
            this.desc = desc;
        }

        private int status;
        private String desc;
    }

    @Getter
    public enum PublishStatusEnum {
        UNPUBLISH(0,"未定价"),
        PUBLISHING(1,"定价中"),
        PUBLISH_FAIL(2,"定价失败"),
        PUBLISH_SUCCESS(3,"定价成功");

        PublishStatusEnum(int status,String desc){
            this.status = status;
            this.desc = desc;
        }

        private int status;
        private String desc;
    }
}
