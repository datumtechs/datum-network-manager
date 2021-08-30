package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.LocalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import com.platon.rosettanet.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.rosettanet.admin.service.constant.ServiceConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/15 12:02
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class LocalDataImportFileResp {

    /**
     * 序号
     */
    @ApiModelProperty(name = "id", value = "序号")
    private Integer id;
    //metaData主id
    @ApiModelProperty(name = "metaDataPKId", value = "metaData主id")
    private Integer metaDataPKId;
    //组织身份ID
    @ApiModelProperty(name = "identityId", value = "组织身份ID")
    private String identityId;
    //源文件ID，上传文件成功后返回源文件ID
    @ApiModelProperty(name = "fileId", value = "源文件ID，上传文件成功后返回源文件ID")
    private String fileId;
    //文件名称
    @ApiModelProperty(name = "fileName", value = "文件名称")
    private String fileName;
    //文件存储路径
    @ApiModelProperty(name = "filePath", value = "文件存储路径")
    private String filePath;
    //文件后缀/类型, csv
    @ApiModelProperty(name = "fileType", value = "文件后缀/类型, csv")
    private String fileType;
    //资源名称
    @ApiModelProperty(name = "resourceName", value = "资源名称")
    private String resourceName;
    //文件大小(字节)
    @ApiModelProperty(name = "size", value = "文件大小(字节)")
    private Long size;
    //数据行数(不算title)
    @ApiModelProperty(name = "rows", value = "数据行数(不算title)")
    private Long rows;
    //数据列数
    @ApiModelProperty(name = "columns", value = "数据列数")
    private Integer columns;
    //是否带标题
    @ApiModelProperty(name = "hasTitle", value = "是否带标题")
    private Boolean hasTitle;
    //数据描述
    @ApiModelProperty(name = "remarks", value = "数据描述")
    private String remarks;
    //数据的状态 (entered：录入数据(创建未发布新表之前的操作); created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)
    @ApiModelProperty(name = "status", value = "数据的状态 (entered：录入数据(创建未发布新表之前的操作); created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)")
    private String status;
    //元数据ID,hash
    @ApiModelProperty(name = "metaDataId", value = "元数据ID,hash")
    private String metaDataId;
    //创建时间
    @ApiModelProperty(name = "recCreateTime", value = "创建时间,单位ms")
    private Long recCreateTime;
    //最后更新时间
    @ApiModelProperty(name = "recUpdateTime", value = "最后更新时间,单位ms")
    private Long recUpdateTime;

    //源文件列信息
    @ApiModelProperty(name = "localMetaDataColumnList", value = "源文件列信息")
    private List<LocalMetaDataColumn> localMetaDataColumnList = new ArrayList<>();

    public static LocalDataImportFileResp from(LocalDataFileDetail detail){
        if(detail == null){
            return null;
        }
        LocalDataImportFileResp resp = new LocalDataImportFileResp();
        BeanUtils.copyProperties(detail,resp);
        resp.setStatus(LocalDataFileStatusEnum.ENTERED.getStatus());
        resp.setResourceName((String) detail.getDynamicFields().get(ServiceConstant.LOCAL_DATA_RESOURCE_NAME));
        resp.setMetaDataPKId((Integer) detail.getDynamicFields().get(ServiceConstant.LOCAL_DATA_METADATA_PK_ID));
        resp.setRecCreateTime(detail.getRecCreateTime() == null? null : detail.getRecCreateTime().getTime());
        resp.setRecUpdateTime(detail.getRecUpdateTime() == null? null : detail.getRecUpdateTime().getTime());
        return resp;
    }
}
