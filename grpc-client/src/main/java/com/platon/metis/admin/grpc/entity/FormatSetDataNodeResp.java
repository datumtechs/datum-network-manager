package com.platon.metis.admin.grpc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lyf
 * @Description TODO
 * @date 2021/7/10 12:08
 */
@Getter
@Setter
@ToString
public class FormatSetDataNodeResp extends CommonResp {
    private RegisteredNodeResp nodeResp;
}
