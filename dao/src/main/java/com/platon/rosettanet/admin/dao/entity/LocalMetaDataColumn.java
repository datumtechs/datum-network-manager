package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;

import java.util.Date;

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
    private Integer metaId;
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
    private Long size;
    /**
     * 列描述
     */
    private String remarks;
    /**
     * 是否对外可见 YES:可见，NO:不可见
     */
    private String visible;
    /**
     * 创建时间
     */
    private Date recCreateTime;
    /**
     * 最后更新时间
     */
    private Date recUpdateTime;

    private static final long serialVersionUID = 1L;

}