package com.platon.datum.admin.dto.resp;

import com.platon.datum.admin.dao.entity.DataNode;
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
public class DataNodeQueryResp {
    @ApiModelProperty(name = "id",value = "流水id")
    private Integer id;

    @ApiModelProperty(name = "nodeId",value = "节点id")
    private String nodeId;

    @ApiModelProperty(name = "nodeName",value = "节点名称")
    private String nodeName;

    @ApiModelProperty(name = "connStatus",value = "节点与调度服务的连接状态（0: 未被调度服务连接上; 1: 连接上）")
    private Integer connStatus;

    @ApiModelProperty(name = "internalIp",value = "内部ip地址")
    private String internalIp;

    @ApiModelProperty(name = "internalPort",value = "内部端口号")
    private Integer internalPort;

    @ApiModelProperty(name = "externalIp",value = "外部ip地址")
    private String externalIp;

    @ApiModelProperty(name = "externalPort",value = "外部端口号")
    private Integer externalPort;

    public static DataNodeQueryResp convert(DataNode dataNode) {
        DataNodeQueryResp resp = new DataNodeQueryResp();
        BeanUtils.copyProperties(dataNode, resp);
        resp.setNodeName(dataNode.getNodeName());
        return resp;
    }

}
