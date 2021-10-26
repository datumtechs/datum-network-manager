package com.platon.metis.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 
 * 全网数据文件表列详细表
 */
@Getter
@Setter
@ToString
public class GlobalMetaDataColumn implements Serializable {

    /**
     * 源文件id
     */
    private String metaDataId;

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
     * 是否对外可见 YES:可见，NO:不可见
     */
    private String visible;


    private static final long serialVersionUID = 1L;
}