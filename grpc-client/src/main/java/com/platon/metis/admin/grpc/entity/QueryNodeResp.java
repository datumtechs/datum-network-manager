package com.platon.metis.admin.grpc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lyf
 * @Description TODO
 * @date 2021/7/10 13:57
 */
@Setter
@Getter
@ToString
public class QueryNodeResp extends CommonResp {
    private List<RegisteredNodeResp> nodeRespList;
}
