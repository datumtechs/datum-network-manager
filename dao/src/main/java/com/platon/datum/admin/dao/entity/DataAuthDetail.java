package com.platon.datum.admin.dao.entity;

import lombok.Data;

import java.util.List;

@Data
public class DataAuthDetail {

    DataAuth dataAuth;
    MetaData metaData;
    DataFile dataFile;
    List<MetaDataColumn> metaDataColumnList;


}