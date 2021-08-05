package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import com.platon.rosettanet.admin.dao.enums.LocalDataFileStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/9 10:33
 * @Version
 * @Desc
 */

@Data
@ApiModel
public class LocalDataPageResp {

    @ApiModelProperty(name = "id", value = "数据ID")
    private Integer id;

    @ApiModelProperty(name = "fileName", value = "源文件名称")
    private String fileName;

    @ApiModelProperty(name = "fileId", value = "源文件Id")
    private String fileId;

    @ApiModelProperty(name = "status", value = "元数据状态:1已发布，0未发布")
    private String status;

    @ApiModelProperty(name = "size", value = "数据大小")
    private Long size;

    @ApiModelProperty(name = "size", value = "元数据最近更新时间")
    private Date recUpdateTime;

    @ApiModelProperty(name = "metaDataId", value = "元数据ID,hash")
    private String metaDataId;

//    @ApiModelProperty(name = "metaDataColumnList", value = "元数据摘要")
//    private List<String> metaDataColumnList = new ArrayList<>();


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
        localDataPageResp.setSize(localDataFile.getSize());
        localDataPageResp.setRecUpdateTime(localDataFile.getRecUpdateTime());
        localDataPageResp.setMetaDataId(localDataFile.getMetaDataId());

//        if(localDataFile instanceof LocalDataFileDetail){
//            LocalDataFileDetail detail = (LocalDataFileDetail)localDataFile;
//            List<String> metaDataColumnList = detail.getLocalMetaDataColumnList().stream()
//                    .map(LocalMetaDataColumn::getColumnName)
//                    .collect(Collectors.toList());
//            localDataPageResp.setMetaDataColumnList(metaDataColumnList);
//        }
        return localDataPageResp;
    }
}
