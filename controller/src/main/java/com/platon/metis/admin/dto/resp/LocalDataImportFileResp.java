package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.entity.LocalDataFile;
import com.platon.metis.admin.dao.entity.LocalMetaDataColumn;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
    //源文件ID，上传文件成功后返回源文件ID
    @ApiModelProperty(name = "fileId", value = "源文件ID，上传文件成功后返回源文件ID")
    private String fileId;

    //组织身份ID
    @ApiModelProperty(name = "identityId", value = "组织身份ID")
    private String identityId;

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
    private Integer rows;
    //数据列数
    @ApiModelProperty(name = "columns", value = "数据列数")
    private Integer columns;
    //是否带标题
    @ApiModelProperty(name = "hasTitle", value = "是否带标题")
    private Boolean hasTitle;
    //数据描述
    @ApiModelProperty(name = "remarks", value = "数据描述")
    private String remarks;
    //元数据的状态 (0: 未知; 1: 还未发布的新表; 2: 已发布的表; 3: 已撤销的表)
    @ApiModelProperty(name = "status", value = "元数据的状态 (0: 未知; 1: 还未发布的新表; 2: 已发布的表; 3: 已撤销的表)")
    private Integer status;
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

    public static LocalDataImportFileResp from(LocalDataFile localDataFile){
        if(localDataFile == null){
            return null;
        }
        LocalDataImportFileResp resp = new LocalDataImportFileResp();
        BeanUtils.copyProperties(localDataFile,resp);
        resp.setStatus(LocalDataFileStatusEnum.ENTERED.getStatus());
        resp.setResourceName(getResourceName(localDataFile.getFileName()));
        //todo：可以查询数据库，local_data_file上传并insert到db后，会由db产生create/update时间
        long milliSeconds = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        resp.setRecCreateTime(milliSeconds);
        resp.setRecUpdateTime(milliSeconds);

        resp.setLocalMetaDataColumnList(localDataFile.getLocalMetaDataColumnList());
        return resp;
    }


    private static String getResourceName(String fileName){
        //导入去掉.csv后缀的文件名称，保存前12个字符作为资源名称
        String resourceName = StringUtils.substring(FilenameUtils.getPrefix(fileName),0,12);
        //因为上层已做资源文件名称校验，故此处暂时不再做校验
        return resourceName;
    }
}
