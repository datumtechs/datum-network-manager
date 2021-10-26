package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.entity.GlobalDataFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author liushuyu
 * @Date 2021/7/9 10:33
 * @Version
 * @Desc
 */

@Data
@ApiModel(value = "全网数据列表返回参数")
public class GlobalDataPageResp {

    @ApiModelProperty(name = "metaDataId", value = "元数据ID,hash")
    private String metaDataId;

    @ApiModelProperty(name = "fileName", value = "源文件名称")
    private String fileName;

    @ApiModelProperty(name = "identityId", value = "组织身份ID")
    private String identityId;

    @ApiModelProperty(name = "orgName", value = "组织名称")
    private String orgName;

    @ApiModelProperty(name = "size", value = "数据大小")
    private Long size;

    @ApiModelProperty(name = "publishTime", value = "元数据发布时间")
    private Date publishTime;

//    @ApiModelProperty(name = "metaDataColumnList", value = "元数据摘要")
//    private List<String> metaDataColumnList = new ArrayList<>();


    public static GlobalDataPageResp from(GlobalDataFile globalDataFile){
        if(globalDataFile == null){
            return null;
        }
        GlobalDataPageResp dataPageResp = new GlobalDataPageResp();
        dataPageResp.setMetaDataId(globalDataFile.getMetaDataId());
        dataPageResp.setFileName(globalDataFile.getResourceName());
        dataPageResp.setSize(globalDataFile.getSize());
        dataPageResp.setPublishTime(globalDataFile.getPublishTime());
        dataPageResp.setIdentityId(globalDataFile.getIdentityId());
        dataPageResp.setOrgName(globalDataFile.getOrgName());

//        if(globalDataFile instanceof GlobalDataFileDetail){
//            GlobalDataFileDetail detail = (GlobalDataFileDetail)globalDataFile;
//            List<String> metaDataColumnList = detail.getMetaDataColumnList().stream()
//                    .map(GlobalMetaDataColumn::getColumnName)
//                    .collect(Collectors.toList());
//            dataPageResp.setMetaDataColumnList(metaDataColumnList);
//        }
        return dataPageResp;
    }
}
