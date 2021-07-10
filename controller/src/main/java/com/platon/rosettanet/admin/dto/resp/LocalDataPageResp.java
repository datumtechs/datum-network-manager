package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/9 10:33
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class LocalDataPageResp {

    //源文件名称
    private String fileName;
    //源文件Id
    private String fileId;
    //元数据状态:已发布，未发布
    private String status;
    //元数据摘要：文件描述
    private String remarks;
    //元数据ID,hash
    private String metaDataId;


    public static LocalDataPageResp from(LocalDataFile localDataFile){
        LocalDataPageResp localDataPageResp = new LocalDataPageResp();
        localDataPageResp.setFileId(localDataFile.getFileId());
        localDataPageResp.setFileName(localDataFile.getFileName());
        localDataPageResp.setStatus(localDataFile.getStatus());
        localDataPageResp.setRemarks(localDataFile.getRemarks());
        localDataPageResp.setMetaDataId(localDataFile.getMetaDataId());
        return localDataPageResp;
    }
}
