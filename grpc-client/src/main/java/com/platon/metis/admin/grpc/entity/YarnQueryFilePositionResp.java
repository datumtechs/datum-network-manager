package com.platon.metis.admin.grpc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/10 17:58
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class YarnQueryFilePositionResp {

    //数据节点IP
    private String ip;
    //数据节点端口
    private int port;
    //待下载文件的绝对路径
    private String filePath;
}
