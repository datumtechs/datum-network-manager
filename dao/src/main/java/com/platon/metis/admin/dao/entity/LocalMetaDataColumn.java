package com.platon.metis.admin.dao.entity;

import lombok.Data;

/**
 * @author
 * 本组织数据文件表列详细表，描述源文件中每一列的列信息
 */
@Data
public class LocalMetaDataColumn {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 元数据自增id--- local_meta_data对应id字段
     */
    private Integer localMetaDataDbId;
    /**
     * 列索引
     */
    private Integer columnIdx;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 列类型
     */
    private String columnType;
    /**
     * 列大小（byte）
     */
    private Integer size;
    /**
     * 列描述
     */
    private String remarks;


    /**
     * 是否选择作为当前元数据的列
     */
    private Boolean visible;

    private static final long serialVersionUID = 1L;

}