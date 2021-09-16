package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.entity.LocalMetaDataItem;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
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
@ApiModel
public class LocalDataPageResp {

    @ApiModelProperty(name = "id", value = "数据ID")
    private Integer id;

    @ApiModelProperty(name = "fileName", value = "元数据名称")
    private String fileName;

    @ApiModelProperty(name = "status", value = "元数据状态:1已发布，0未发布")
    private String status;

    @ApiModelProperty(name = "size", value = "数据大小")
    private Long size;

    @ApiModelProperty(name = "size", value = "元数据最近更新时间")
    private Date recUpdateTime;

    @ApiModelProperty(name = "attendTaskCount", value = "参与任务数量")
    private Integer attendTaskCount;

    @ApiModelProperty(name = "metaDataId", value = "元数据ID,hash")
    private String metaDataId;




    public static LocalDataPageResp from(LocalMetaDataItem localMetaDataItem){
        if(localMetaDataItem == null){
            return null;
        }
        LocalDataPageResp localDataPageResp = new LocalDataPageResp();
        localDataPageResp.setId(localMetaDataItem.getId());
        localDataPageResp.setFileName(localMetaDataItem.getMetaDataName());
        localDataPageResp.setMetaDataId(localMetaDataItem.getMetaDataId());
        //元数据状态:1已发布，0未发布
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(localMetaDataItem.getStatus())){
            localDataPageResp.setStatus("1");
        } else {
            localDataPageResp.setStatus("0");
        }
        localDataPageResp.setSize(localMetaDataItem.getSize());
        localDataPageResp.setRecUpdateTime(localMetaDataItem.getRecUpdateTime());
        localDataPageResp.setAttendTaskCount(localMetaDataItem.getDataJoinTaskCount());
        return localDataPageResp;
    }
}
