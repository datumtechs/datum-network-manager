package com.platon.rosettanet.admin.service;

import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LocalDataService {

    List<LocalDataFile> listDataFile(int pageNo, int pageSize);
    int listDataFileCount();

    void uploadFile(MultipartFile file, LocalDataFile localDataFile, List<LocalMetaDataColumn> localMetaDataColumnList);
}
