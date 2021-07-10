package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LocalDataService {

    /**
     * 获取本地组织数据分页列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<LocalDataFile> listDataFile(int pageNo, int pageSize);

    /**
     * 上传源文件到数据节点
     * @param file 待上传的源文件
     * @param hasTitle 源文件是否包含表头
     */
    void uploadFile(MultipartFile file,boolean hasTitle);
}
