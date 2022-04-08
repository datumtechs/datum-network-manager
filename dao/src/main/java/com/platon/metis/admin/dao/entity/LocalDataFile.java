package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocalDataFile extends BaseDomain {
    //全网唯一ID，上传文件成功后返回
    private String fileId;
    //组织身份ID
    private String identityId;
    //文件名称
    private String fileName;
    //文件存储路径
    private String filePath;
    //文件后缀/类型, 0未知、1：csv目前只支持这个
    private Integer fileType;
    //文件大小(字节)
    private Long size;
    //数据行数(不算title)
    private Integer rows;
    //数据列数
    private Integer columns;
    //是否带标题
    private Boolean hasTitle;
    //创建时间
    private Date recCreateTime;
    //最后更新时间
    private Date recUpdateTime;
    //v0.4.0文件内容hash
    private String dataHash;
    //v0.4.0源数据的存储位置类型 (组织本地服务器、远端服务器、云等)
    private Integer locationType;

    //源文件列信息
    private List<LocalMetaDataColumn> localMetaDataColumnList;

    public void addLocalMetaDataColumn(LocalMetaDataColumn localMetaDataColumn){
        if(localMetaDataColumnList==null){
            localMetaDataColumnList = new ArrayList<>();
        }
        localMetaDataColumnList.add(localMetaDataColumn);
    }

    public static enum FileTypeEnum {
        UNKNOWN(0, "unknown"),CSV(1, "csv");
        private int code;
        private String value;

        FileTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
}