package com.platon.rosettanet.admin.dao.entity;

import com.platon.rosettanet.admin.dao.BaseDomain;
import lombok.Data;

import java.util.Date;

@Data
public class LocalDataFile extends BaseDomain {
    /**
     * 序号
     */
    private Integer id;
    //组织身份ID
    private String identityId;
    //源文件ID，上传文件成功后返回源文件ID
    private String fileId;
    //文件名称
    private String fileName;
    //文件存储路径
    private String filePath;
    //文件后缀/类型, csv
    private String fileType;
    //资源名称
    private String resourceName;
    //文件大小(字节)
    private Long size;
    //数据行数(不算title)
    private Long rows;
    //数据列数
    private Integer columns;
    //是否带标题
    private Boolean hasTitle;
    //创建时间
    private Date recCreateTime;
    //最后更新时间
    private Date recUpdateTime;

}