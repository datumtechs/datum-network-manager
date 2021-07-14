package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.DataNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * @author lyf
 * @Description 数据节点查询返回实体
 * @date 2021/7/13 13:50
 */
@Getter
@Setter
@ToString
public class LocalDataNodeQueryResp {
    private Integer id;

    private String nodeId;

    private String nodeName;

    private String connStatus;

    private String internalIp;

    private Integer internalPort;

    private String externalIp;

    private Integer externalPort;

    public static LocalDataNodeQueryResp convert(DataNode dataNode) {
        LocalDataNodeQueryResp resp = new LocalDataNodeQueryResp();
        BeanUtils.copyProperties(dataNode, resp);
        resp.setNodeName(dataNode.getHostName());
        return resp;
    }

}
