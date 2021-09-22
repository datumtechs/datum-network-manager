package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.entity.LocalOrg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;


/**
 * @Author liushuyu
 * @Date 2021/7/3 14:56
 * @Version
 * @Desc
 */

@Data
@ApiModel
public class SystemQueryBaseInfoResp {

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

    @ApiModelProperty(value = "连接状态 enabled：可用, disabled:不可用")
    private String carrierConnStatus;

    @ApiModelProperty(value = "连接节点的数量")
    private Long connNodeCount;

    @ApiModelProperty(value = "服务连接时间")
    private String carrierConnTime;

    @ApiModelProperty(value = "调度服务的状态：active: 活跃; leave: 离开网络; join: 加入网络 unuseful: 不可用")
    private String carrierStatus;

    @ApiModelProperty(value = "最后更新时间,单位ms")
    private Long recUpdateTime;

    @ApiModelProperty(value = "组织入网状态：0未入网，1已入网")
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
