package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.dao.LocalDataFileMapper;
import com.platon.rosettanet.admin.dao.LocalMetaDataColumnMapper;
import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import com.platon.rosettanet.admin.service.LocalDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@Transactional
public class LocalDataServiceImpl implements LocalDataService {

    private final Path path = Paths.get("fileStorage");

    @Autowired
    private LocalDataFileMapper localDataFileMapper;

    @Autowired
    private LocalMetaDataColumnMapper localMetaDataColumnMapper;

    @Override
    public List<LocalDataFile> listDataFile(int pageNo, int pageSize) {
        int offset = (pageNo-1) * pageSize;
        return localDataFileMapper.listDataFile(offset, pageSize);
    }

    @Override
    public int listDataFileCount() {
        return localDataFileMapper.listDataFileCount();
    }

    @Override
    public void uploadFile(MultipartFile file, LocalDataFile localDataFile, List<LocalMetaDataColumn> localMetaDataColumnList) {
        try {
            Path destPath = this.path.resolve(file.getName());
            file.transferTo(destPath);

            long fileLength = localDataFile.getSize();
            try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(destPath.toFile()))){
                lineNumberReader.skip(fileLength);
                long lines = lineNumberReader.getLineNumber();
                if(localDataFile.getHasTitle() && lines >= 1){
                    localDataFile.setRows(lines - 1);
                }else{
                    localDataFile.setRows(lines);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            log.error("Could not save the uploaded file:{}", file.getOriginalFilename(), e);
            throw new RuntimeException("Could not save the uploaded file. Error:"+e.getMessage());
        }

        //todo: generate metadataID
        String metadataId = "0x0201213";
        localDataFile.setMetaDataId(metadataId);
        localDataFileMapper.insert(localDataFile);

        localMetaDataColumnList.forEach(column -> {column.setMetaDataId(metadataId);});

        localMetaDataColumnMapper.insertBatch(localMetaDataColumnList);

    }
}
