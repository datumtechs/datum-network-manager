package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
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

}