package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;

import java.util.List;

@Data
public class LocalDataAuthDetail{

    LocalDataAuth localDataAuth;
    LocalMetaData localMetaData;
    LocalDataFile localDataFile;
    List<LocalMetaDataColumn> localMetaDataColumnList;


}