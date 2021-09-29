package com.platon.metis.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 
 * 全网数据文件表
 */
@Getter
@Setter
@ToString
public class GlobalDataFile implements Serializable {
    /**
     * 序号
     */
    private Integer id;

    /**
     * 组织身份ID
     */
    private String identityId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 源文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文件后缀/类型, 0未知、1：csv目前只支持这个
     */
    private Integer fileType;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 文件大小(字节)
     */
    private Long size;

    /**
     * 数据行数(不算title)
     */
    private Integer rows;

    /**
     * 数据列数
     */
    private Integer columns;

    /**
     * 是否带标题
     */
    private Boolean hasTitle;

    /**
     * 数据描述
     */
    private String remarks;

    /**
     * 元数据的状态 (0: 未知; 1: 还未发布的新表; 2: 已发布的表; 3: 已撤销的表)
     */
    private Integer status;

    /**
     * 元数据发布时间
     */
    private Date publishTime;

    /**
     * 元数据ID,hash
     */
    private String metaDataId;

    /**
     * 创建时间
     */
    private Date recCreateTime;

    /**
     * 最后更新时间
     */
    private Date recUpdateTime;

    //源文件列信息
    private List<GlobalMetaDataColumn> metaDataColumnList = new ArrayList<>();

    private static final long serialVersionUID = 1L;
}