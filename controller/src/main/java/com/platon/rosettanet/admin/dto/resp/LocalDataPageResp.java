package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import com.platon.rosettanet.admin.dao.enums.LocalDataFileStatusEnum;
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
public class LocalDataPageResp {

    //数据ID
    @ApiModelProperty(name = "id", value = "数据ID")
    private Integer id;
    //源文件名称
    @ApiModelProperty(name = "fileName", value = "源文件名称")
    private String fileName;
    //源文件Id
    @ApiModelProperty(name = "fileId", value = "源文件Id")
    private String fileId;
    //元数据状态:已发布，未发布
    @ApiModelProperty(name = "status", value = "元数据状态:1已发布，0未发布")
    private String status;
    //元数据摘要：文件描述
    @ApiModelProperty(name = "remarks", value = "元数据摘要：文件描述")
    private String remarks;
    //元数据ID,hash
    @ApiModelProperty(name = "metaDataId", value = "元数据ID,hash")
    private String metaDataId;
    //元数据摘要
    @ApiModelProperty(name = "metaDataColumnList", value = "元数据摘要")
    private List<String> metaDataColumnList = new ArrayList<>();


    public static LocalDataPageResp from(LocalDataFile localDataFile){
        if(localDataFile == null){
            return null;
        }
        LocalDataPageResp localDataPageResp = new LocalDataPageResp();
        localDataPageResp.setId(localDataFile.getId());
        localDataPageResp.setFileId(localDataFile.getFileId());
        localDataPageResp.setFileName(localDataFile.getResourceName());
        //元数据状态:1已发布，0未发布
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(localDataFile.getStatus())){
            localDataPageResp.setStatus("1");
        } else {
            localDataPageResp.setStatus("0");
        }
        localDataPageResp.setRemarks(localDataFile.getRemarks());
        localDataPageResp.setMetaDataId(localDataFile.getMetaDataId());

        if(localDataFile instanceof LocalDataFileDetail){
            LocalDataFileDetail detail = (LocalDataFileDetail)localDataFile;
            List<String> metaDataColumnList = detail.getLocalMetaDataColumnList().stream()
                    .map(LocalMetaDataColumn::getColumnName)
                    .collect(Collectors.toList());
            localDataPageResp.setMetaDataColumnList(metaDataColumnList);
        }
        return localDataPageResp;
    }
}
