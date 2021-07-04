package com.platon.rosettanet.admin.controller;

import com.platon.rosettanet.admin.dao.entity.DataFileStatus;
import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.UploadDataFileReq;
import com.platon.rosettanet.admin.service.LocalDataService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource/mydata")
public class LocalDataController {

    @Autowired
    private LocalDataService localDataService;

    @GetMapping("/metaDataList.json")
    public JsonResponse<List<LocalDataFile>> listDataFile(@RequestParam int pageNo, @RequestParam  int pageSize) {
        List<LocalDataFile> dataFileList = localDataService.listDataFile(pageNo, pageSize);
        if (CollectionUtils.isNotEmpty(dataFileList)){
            int count = localDataService.listDataFileCount();
            JsonResponse response = JsonResponse.success(dataFileList);
            response.setPagination(pageNo, pageSize, count);
            return response;
        }
        return JsonResponse.success();
    }

    @PostMapping("/uploadFile")
    public JsonResponse uploadFile(UploadDataFileReq uploadDataFileReq) {
        if (uploadDataFileReq.getFile().isEmpty()) {
            throw new RuntimeException("上传失败，请选择文件");
        }

        LocalDataFile localDataFile = new LocalDataFile();
        localDataFile.setColumns(uploadDataFileReq.getLocalMetaDataColumnList().size());
        localDataFile.setResourceName(uploadDataFileReq.getMetadataName());
        localDataFile.setFileName( uploadDataFileReq.getFile().getName()); //getOriginalFilename()返回带路径的文件名
        localDataFile.setSize(uploadDataFileReq.getFile().getSize());
        localDataFile.setStatus(DataFileStatus.created);
        localDataService.uploadFile(uploadDataFileReq.getFile(), localDataFile, uploadDataFileReq.getLocalMetaDataColumnList());
        return JsonResponse.success();
    }
}
