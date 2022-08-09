package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
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
public class DataToken extends BaseDomain {

    //id
    @ApiModelProperty("id")
    private int id;

    //type
    @ApiModelProperty("凭证类型：1-无属性凭证，2-有属性凭证，3-都支持")
    private int type;

    //地址
    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("名称")
    //名称
    private String name;

    @ApiModelProperty("符号")
    //符号
    private String symbol;

    @ApiModelProperty("初始发行量")
    //总发行量
    private String init;

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

    @ApiModelProperty("对应的metaData表Id")
    //对应的metaData表Id
    private Integer metaDataDbId;

    @ApiModelProperty("持有人数量")
    //持有人数量
    private long holder;

    /**
     * 状态：1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功，7-绑定中，8-绑定失败，9-绑定成功
     * {@link StatusEnum}
     */
    @ApiModelProperty("状态：1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功，7-绑定中，8-绑定失败，9-绑定成功")
    private int status;

    @ApiModelProperty("发布合约的交易nonce")
    private Integer publishNonce;

    @ApiModelProperty("发布合约的交易hash")
    private String publishHash;

    @ApiModelProperty("上架dex的交易nonce")
    private Integer upNonce;

    @ApiModelProperty("上架dex的交易hash")
    private String upHash;

    //创建时间
    @ApiModelProperty("创建时间")
    private LocalDateTime recCreateTime;

    //更新时间
    @ApiModelProperty("更新时间")
    private LocalDateTime recUpdateTime;

    //前端显示
    @ApiModelProperty("明文费用")
    private String plaintextFee;
    //前端显示
    @ApiModelProperty("密文费用")
    private String ciphertextFee;
    @ApiModelProperty("想要修改成的明文费用")
    private String newPlaintextFee;
    @ApiModelProperty("想要修改成的密文费用")
    private String newCiphertextFee;
    @ApiModelProperty("明文和密文消耗量上一次修改时间")
    private LocalDateTime feeUpdateTime;

    @Getter
    @ToString
    public enum StatusEnum {
        PUBLISHING(1, "发布中"),
        PUBLISH_FAIL(2, "发布失败"),
        PUBLISH_SUCCESS(3, "发布成功"),
        PRICING(4, "定价中"),
        PRICE_FAIL(5, "定价失败"),
        PRICE_SUCCESS(6, "定价成功"),
        BINDING(7, "绑定中"),
        BIND_FAIL(8, "绑定失败"),
        BIND_SUCCESS(9, "绑定成功"),
        ;

        StatusEnum(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        private int status;
        private String desc;
    }
}
