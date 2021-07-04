package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class GlobalMetaDataColumn {
    private Integer id;

    private Integer dataFileId;

    private String metaDataId;

    private Integer columnIdx;

    private String columnName;

    private String columnType;

    private Long size;

    private String remarks;

    private String visible;

    private LocalDateTime recCreateTime;

    private LocalDateTime recUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDataFileId() {
        return dataFileId;
    }

    public void setDataFileId(Integer dataFileId) {
        this.dataFileId = dataFileId;
    }

    public String getMetaDataId() {
        return metaDataId;
    }

    public void setMetaDataId(String metaDataId) {
        this.metaDataId = metaDataId;
    }

    public Integer getColumnIdx() {
        return columnIdx;
    }

    public void setColumnIdx(Integer columnIdx) {
        this.columnIdx = columnIdx;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public LocalDateTime getRecCreateTime() {
        return recCreateTime;
    }

    public void setRecCreateTime(LocalDateTime recCreateTime) {
        this.recCreateTime = recCreateTime;
    }

    public LocalDateTime getRecUpdateTime() {
        return recUpdateTime;
    }

    public void setRecUpdateTime(LocalDateTime recUpdateTime) {
        this.recUpdateTime = recUpdateTime;
    }
}