package com.platon.datum.admin.dao.entity;

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
        DEX_ROUTER_ADDRESS("dex_router_address", "router合约地址"),
        DEX_WEB_URL("dex_web_url","dex链接地址"),
        CHAIN_NAME("chain_name","链名称"),
        CHAIN_ID("chain_id","链ID"),
        RPC_URL("rpc_url","链rpcUrl，给用户使用，必须是外部ip"),
        SYMBOL("symbol","主币symbol"),
        BLOCK_EXPLORER_URL("block_explorer_url","区块链浏览器地址"),
        HRP("hrp","链hrp"),
        RPC_URL_LIST("rpc_url_list","链rpcUrl，主要是给后台系统用，可以是内部IP，例子：http://localhost:6789,多个url用逗号分割"),
        ATTRIBUTE_DATA_TOKEN_FACTORY_ADDRESS("attribute_data_token_factory_address","有属性凭证工厂合约地址"),
        ;

        KeyEnum(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        private String key;
        private String desc;
    }

}
