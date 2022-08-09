package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 数据凭证表
 * @author liushuyu
 */
@Getter
@Setter
@ToString
@ApiModel
public class AttributeDataToken extends BaseDomain {
    /**
     * dataTokenId
     */
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 合约地址
     */
    @ApiModelProperty("合约地址")
    private String address;

    /**
     * 合约名称
     */
    @ApiModelProperty("合约名称")
    private String name;

    /**
     * 合约符号
     */
    @ApiModelProperty("合约符号")
    private String symbol;

    /**
     * 总发行量
     */
    @ApiModelProperty("总发行量")
    private String total;

    /**
     * 价值证明描述
     */
    @ApiModelProperty("价值证明描述")
    private String desc;

    /**
     * 拥有者钱包地址
     */
    @ApiModelProperty("拥有者钱包地址")
    private String owner;

    /**
     * 对应的metaData表中的Id
     */
    @ApiModelProperty("对应的metaData表中的Id")
    private Integer metaDataDbId;

    /**
     * 发布凭证交易nonce
     */
    @ApiModelProperty("发布凭证交易nonce")
    private Integer publishNonce;

    /**
     * 发布上链的交易hash，供查询发布状态
     */
    @ApiModelProperty("发布上链的交易hash，供查询发布状态")
    private String publishHash;

    /**
     * 状态：1-发布中，2-发布失败，3-发布成功，4-绑定中，5-绑定失败，6-绑定成功
     * {@link StatusEnum}
     */
    @ApiModelProperty("状态：1-发布中，2-发布失败，3-发布成功，4-绑定中，5-绑定失败，6-绑定成功")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime recCreateTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime recUpdateTime;

    @Getter
    @ToString
    public enum StatusEnum {
        PUBLISHING(1, "发布中"),
        PUBLISH_FAIL(2, "发布失败"),
        PUBLISH_SUCCESS(3, "发布成功"),
        BINDING(4, "绑定中"),
        BIND_FAIL(5, "绑定失败"),
        BIND_SUCCESS(6, "绑定成功"),
        ;

        StatusEnum(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        private int status;
        private String desc;
    }
}
