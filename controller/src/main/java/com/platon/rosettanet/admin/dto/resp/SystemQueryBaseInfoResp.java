package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:56
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class SystemQueryBaseInfoResp {

    //机构名称
    @ApiModelProperty(name = "name", value = "机构名称")
    private String name;
    //机构身份标识ID
    @ApiModelProperty(name = "identityId", value = "机构身份标识ID")
    private String identityId;
    //机构调度服务node id，入网后可以获取到
    @ApiModelProperty(name = "carrierNodeId", value = "机构调度服务node id，入网后可以获取到")
    private String carrierNodeId;
    //调度服务IP地址
    @ApiModelProperty(name = "carrierIp", value = "调度服务IP地址")
    private String carrierIp;
    //调度服务端口号
    @ApiModelProperty(name = "carrierPort", value = "调度服务端口号")
    private Integer carrierPort;
    //连接状态 enabled：可用, disabled:不可用
    @ApiModelProperty(name = "carrierConnStatus", value = "连接状态 enabled：可用, disabled:不可用")
    private String carrierConnStatus;
    //服务连接时间
    @ApiModelProperty(name = "carrierConnTime", value = "服务连接时间")
    private String carrierConnTime;
    //调度服务的状态：active: 活跃; leave: 离开网络; join: 加入网络 unuseful: 不可用
    @ApiModelProperty(name = "carrierStatus", value = "调度服务的状态：active: 活跃; leave: 离开网络; join: 加入网络 unuseful: 不可用")
    private String carrierStatus;
    //最后更新时间
    @ApiModelProperty(name = "recUpdateTime", value = "最后更新时间,单位ms")
    private Long recUpdateTime;
    //最后更新时间
    @ApiModelProperty(name = "status", value = "组织入网状态：0未入网，1已入网")
    private Integer status;

    public static SystemQueryBaseInfoResp from(LocalOrg localOrg){
        if(localOrg == null){
            return null;
        }
        SystemQueryBaseInfoResp resp = new SystemQueryBaseInfoResp();
        BeanUtils.copyProperties(localOrg,resp);
        resp.setRecUpdateTime(localOrg.getRecUpdateTime() == null? null : localOrg.getRecUpdateTime().getTime());
        return resp;
    }
}
