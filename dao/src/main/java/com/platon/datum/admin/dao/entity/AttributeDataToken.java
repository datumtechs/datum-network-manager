package com.platon.datum.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author liushuyu
 * @Date 2022/7/13 14:54
 * @Version
 * @Desc ******************************
 */

/**
 * 数据凭证表
 */
@Getter
@Setter
@ToString
public class AttributeDataToken {
    /**
     * dataTokenId
     */
    private Integer id;

    /**
     * 合约地址
     */
    private String address;

    /**
     * 合约名称
     */
    private String name;

    /**
     * 合约符号
     */
    private String symbol;

    /**
     * 总发行量
     */
    private String total;

    /**
     * 价值证明描述
     */
    private String desc;

    /**
     * 拥有者钱包地址
     */
    private String owner;

    /**
     * 对应的metaData表中的Id
     */
    private Integer metaDataDbId;

    /**
     * 持有人数量
     */
    private Integer holder;

    /**
     * 发布凭证交易nonce
     */
    private Integer publishNonce;

    /**
     * 发布上链的交易hash，供查询发布状态
     */
    private String publishHash;

    /**
     * 状态：0-未发布，1-发布中，2-发布失败，3-发布成功
     */
    private Integer status;

    private LocalDateTime recCreateTime;

    private LocalDateTime recUpdateTime;

    @Getter
    @ToString
    public enum StatusEnum {
        UNPUBLISH(0, "未发布"),
        PUBLISHING(1, "发布中"),
        PUBLISH_FAIL(2, "发布失败"),
        PUBLISH_SUCCESS(3, "发布成功"),;

        StatusEnum(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        private int status;
        private String desc;
    }
}