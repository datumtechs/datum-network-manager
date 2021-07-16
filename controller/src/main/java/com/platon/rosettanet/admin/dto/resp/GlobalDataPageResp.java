package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.GlobalMetaDataColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/9 10:33
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class GlobalDataPageResp {

    //源文件名称
    @ApiModelProperty(name = "fileName", value = "源文件名称")
    private String fileName;
    //元数据摘要：文件描述
    @ApiModelProperty(name = "remarks", value = "元数据摘要：文件描述")
    private String remarks;
    //元数据ID,hash
    @ApiModelProperty(name = "metaDataId", value = "元数据ID,hash")
    private String metaDataId;
    /**
     * 组织身份ID
     */
    @ApiModelProperty(name = "identityId", value = "组织身份ID")
    private String identityId;
    //组织名称
    @ApiModelProperty(name = "orgName", value = "组织名称")
    private String orgName;
    //元数据摘要
    @ApiModelProperty(name = "metaDataColumnList", value = "元数据摘要")
    private List<String> metaDataColumnList = new ArrayList<>();


    public static GlobalDataPageResp from(GlobalDataFile globalDataFile){
        if(globalDataFile == null){
            return null;
        }
        GlobalDataPageResp dataPageResp = new GlobalDataPageResp();
        dataPageResp.setFileName(globalDataFile.getResourceName());
        dataPageResp.setRemarks(globalDataFile.getRemarks());
        dataPageResp.setMetaDataId(globalDataFile.getMetaDataId());
        dataPageResp.setIdentityId(globalDataFile.getIdentityId());
        dataPageResp.setOrgName(globalDataFile.getOrgName());

        if(globalDataFile instanceof GlobalDataFileDetail){
            GlobalDataFileDetail detail = (GlobalDataFileDetail)globalDataFile;
            List<String> metaDataColumnList = detail.getMetaDataColumnList().stream()
                    .map(GlobalMetaDataColumn::getColumnName)
                    .collect(Collectors.toList());
            dataPageResp.setMetaDataColumnList(metaDataColumnList);
        }
        return dataPageResp;
    }
}
