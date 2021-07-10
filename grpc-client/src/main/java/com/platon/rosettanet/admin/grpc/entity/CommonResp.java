package com.platon.rosettanet.admin.grpc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lyf
 * @Description 通用响应类
 * @date 2021/7/10 12:26
 */
@Getter
@Setter
@ToString
public class CommonResp {
    public int status;

    public String msg;
}
