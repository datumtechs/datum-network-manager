package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
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
public class GlobalDataPageResp {

    //源文件名称
    private String fileName;
    //元数据摘要：文件描述
    private String remarks;
    //元数据ID,hash
    private String metaDataId;
    /**
     * 组织身份ID
     */
    private String identityId;


    public static GlobalDataPageResp from(GlobalDataFile globalDataFile){
        GlobalDataPageResp localDataPageResp = new GlobalDataPageResp();
        localDataPageResp.setFileName(globalDataFile.getResourceName());
        localDataPageResp.setRemarks(globalDataFile.getRemarks());
        localDataPageResp.setMetaDataId(globalDataFile.getMetaDataId());
        localDataPageResp.setIdentityId(globalDataFile.getIdentityId());
        return localDataPageResp;
    }
}
