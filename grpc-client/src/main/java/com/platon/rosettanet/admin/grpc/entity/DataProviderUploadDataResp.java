package com.platon.rosettanet.admin.grpc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/9 17:27
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class DataProviderUploadDataResp {

    //源文件id
    private String fileId;
    //源文件路径
    private String filePath;
}
