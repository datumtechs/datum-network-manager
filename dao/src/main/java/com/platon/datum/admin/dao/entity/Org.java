package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author juzix
 * @Date 2022/8/9 10:32
 * @Version
 * @Desc ******************************
 */

@Getter
@Setter
@ToString
@ApiModel
public class Org extends BaseDomain {
    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    private String name;

    /**
     * 机构身份标识ID
     */
    @ApiModelProperty("机构身份标识ID")
    private String identityId;

    /**
     * 机构调度服务node id，入网后可以获取到
     */
    @ApiModelProperty("机构调度服务node id，入网后可以获取到")
    private String carrierNodeId;

    /**
     * 调度服务IP地址
     */
    @ApiModelProperty("调度服务IP地址")
    private String carrierIp;

    /**
     * 调度服务端口号
     */
    @ApiModelProperty("调度服务端口号")
    private Integer carrierPort;

    /**
     * 连接状态 enabled：可用, disabled:不可用
     */
    @ApiModelProperty("连接状态 enabled：可用, disabled:不可用")
    private String carrierConnStatus;

    /**
     * 调度服务的状态（0:unknown未知、1: active活跃、2:leave: 离开网络、3:join加入网络 4:unuseful不可用）
     */
    @ApiModelProperty("调度服务的状态（0:unknown未知、1: active活跃、2:leave: 离开网络、3:join加入网络 4:unuseful不可用）")
    private Integer carrierStatus;

    /**
     * 节点连接的数量
     */
    @ApiModelProperty("节点连接的数量")
    private Integer connNodeCount;

    /**
     * 服务连接时间
     */
    @ApiModelProperty("服务连接时间")
    private LocalDateTime carrierConnTime;

    /**
     * 0未入网，1已入网， 99已退网
     */
    @ApiModelProperty("0未入网，1已入网， 99已退网")
    private Integer status;

    /**
     * 当前系统的本地节点，可以作为引导节点提供给三方节点
     */
    @ApiModelProperty("当前系统的本地节点，可以作为引导节点提供给三方节点")
    private String localBootstrapNode;

    /**
     * 当前系统本地的
     */
    @ApiModelProperty("当前系统本地的")
    private String localMultiAddr;

    /**
     * 组织机构图像url
     */
    @ApiModelProperty("组织机构图像url")
    private String imageUrl;

    /**
     * 组织机构简介
     */
    @ApiModelProperty("组织机构简介")
    private String profile;

    /**
     * 最后更新时间
     */
    @ApiModelProperty("最后更新时间")
    private LocalDateTime recUpdateTime;

    /**
     * 调度服务钱包地址
     */
    @ApiModelProperty("调度服务钱包地址")
    private String observerProxyWalletAddress;

    /**
     * 是否是委员会成员：0-否，1-是
     */
    @ApiModelProperty("是否是委员会成员：0-否，1-是")
    private Integer isAuthority;

    /**
     * vc json字符串
     */
    @ApiModelProperty("vc json字符串")
    private String credential;

    @Getter
    public enum StatusEnum {
        NOT_CONNECT_NET(0, "待接入网络"),
        CONNECTED(1, "已入网"),
        LEFT_NET(99, "已退网，待重新接入网络");

        private int code;
        private String desc;

        StatusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String toString() {
            return code + "-" + desc;
        }
    }
}