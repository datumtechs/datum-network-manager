package com.platon.metis.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author liushuyu
 * @Date 2022/4/2 16:53
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class SysConfig {

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("配置的键")
    private String key;

    @ApiModelProperty("配置的值")
    private String value;

    @ApiModelProperty("配置的状态：0-失效，1-有效")
    private int status;

    @ApiModelProperty("配置的描述")
    private String desc;

    @ApiModelProperty("创建时间")
    private LocalDateTime recCreateTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime recUpdateTime;

    @Getter
    @ToString
    public enum StatusEnum {
        VALID(1, "有效"), INVALID(0, "失效"),
        ;

        StatusEnum(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        private int status;
        private String desc;
    }

    @Getter
    @ToString
    public enum KeyEnum {
        DATA_TOKEN_FACTORY_ADDRESS("data_token_factory_address", "凭证工厂合约地址"),
        DEX_ROUTER_ADDRESS("data_token_factory_address", "router合约地址"),
        DEX_WEB_URL("dex_web_url","dex链接地址"),
        ;

        KeyEnum(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        private String key;
        private String desc;
    }

}
