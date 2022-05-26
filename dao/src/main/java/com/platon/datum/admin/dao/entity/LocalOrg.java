package com.platon.datum.admin.dao.entity;


import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * @author houz
 */
@Data
@ApiModel
public class LocalOrg extends BaseDomain {
    @ApiModelProperty(value = "机构名称")
    private String name;

    @ApiModelProperty(value = "机构身份标识ID")
    private String identityId;

    @ApiModelProperty(value = "机构调度服务node id，入网后可以获取到")
    private String carrierNodeId;

    @ApiModelProperty(value = "调度服务IP地址")
    private String carrierIp;

    @ApiModelProperty(value = "调度服务端口号")
    private Integer carrierPort;

    @ApiModelProperty(value = "管理台连接调度服务的状态 enabled：能连接, disabled:不能连接")
    private String carrierConnStatus;

    @ApiModelProperty(value = "调度服务连接其它节点的数量")
    private Integer connNodeCount;

    @ApiModelProperty(value = "管理台最近连接调度服务的时间")
    private Date carrierConnTime;

    @ApiModelProperty(value = "调度服务本身的状态：0:unknown未知、1: active活跃、2:leave: 离开网络、3:join加入网络 4:unuseful不可用")
    private Integer carrierStatus;

    @ApiModelProperty(value = "最后更新时间,单位ms")
    private Date recUpdateTime;

    @ApiModelProperty(value = "本地组织入网状态：0未加入网，1已经加入网，99已退出网络（还可以重新加入）")
    private Integer status;

    @ApiModelProperty(value = "本地组织图像url")
    private String imageUrl;

    @ApiModelProperty(value = "本地组织简介")
    private String profile;

    @ApiModelProperty(value = "当前系统的本地节点，可以作为引导节点提供给三方节点")
    private String localBootstrapNode;

    @ApiModelProperty(value = "当前系统本地的 multiAddr", example = "/ip4/127.0.0.1/tcp/18001/p2p/16Uiu2HAmKMo4Ci5TYcXspnKnMWK4G4CRWSHBV4qCUAFYrtirhcuz")
    private String localMultiAddr;

    @ApiModelProperty(value = "carrier钱包地址")
    private String carrierWallet;

    @Getter
    public enum Status{
        NOT_CONNECT_NET(0, "待接入网络"),
        CONNECTED(1, "已入网"),
        LEFT_NET(99, "已退网，待重新接入网络");

        private final int code;
        private final String desc;

        Status(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String toString() {
            return code + "-" + desc;
        }
    }
}