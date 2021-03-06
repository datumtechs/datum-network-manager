package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.entity.DataNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author lyf
 * @Description 数据节点查询返回实体
 * @date 2021/7/13 13:50
 */

@Data
@ApiModel(value = "数据节点查询返回实体")
public class LocalDataNodeQueryResp {
    @ApiModelProperty(name = "id",value = "流水id")
    private Integer id;

    @ApiModelProperty(name = "nodeId",value = "节点id")
    private String nodeId;

    @ApiModelProperty(name = "nodeName",value = "节点名称")
    private String nodeName;

    @ApiModelProperty(name = "connStatus",value = "网络连接状态，0 网络连接成功，-1 网络连接失败")
    private String connStatus;

    @ApiModelProperty(name = "internalIp",value = "内部ip地址")
    private String internalIp;

    @ApiModelProperty(name = "internalPort",value = "内部端口号")
    private Integer internalPort;

    @ApiModelProperty(name = "externalIp",value = "外部ip地址")
    private String externalIp;

    @ApiModelProperty(name = "externalPort",value = "外部端口号")
    private Integer externalPort;

    public static LocalDataNodeQueryResp convert(DataNode dataNode) {
        LocalDataNodeQueryResp resp = new LocalDataNodeQueryResp();
        BeanUtils.copyProperties(dataNode, resp);
        resp.setNodeName(dataNode.getHostName());
        return resp;
    }

}
