package com.platon.datum.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 本组织数据文件表列详细表，描述源文件中每一列的列信息
 */
@Data
@ApiModel
public class MetaDataColumn {
    /**
     * 序号
     */
    @ApiModelProperty(name = "id", value = "PK,记录ID")
    private Integer id;
    /**
     * 元数据自增id--- local_meta_data对应id字段
     */
    @ApiModelProperty(name = "metaDataDbId", value = "所属元数据的记录ID")
    private Integer metaDataDbId;
    /**
     * 列索引
     */

    @ApiModelProperty(name = "columnIdx", value = "列索引")
    private Integer columnIdx;
    /**
     * 列名
     */
    @ApiModelProperty(name = "columnName", value = "列名")
    private String columnName;
    /**
     * 列类型
     */
    @ApiModelProperty(name = "columnType", value = "列类型")
    private String columnType;
    /**
     * 列大小（byte）
     */
    @ApiModelProperty(name = "size", value = "列大小（byte）")
    private Integer size;
    /**
     * 列描述
     */
    @ApiModelProperty(name = "remarks", value = "列描述")
    private String remarks;


    /**
     * 是否选择作为当前元数据的列
     */
    @ApiModelProperty(name = "visible", value = "是否选择作为当前元数据的列")
    private Boolean visible;

    private static final long serialVersionUID = 1L;

}