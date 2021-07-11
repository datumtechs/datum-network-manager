package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalDataFileDetail;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface LocalDataService {

    /**
     * 获取本地组织数据分页列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<LocalDataFile> listDataFile(int pageNo, int pageSize,String keyword);

    /**
     * 上传源文件到数据节点
     * @param file 待上传的源文件
     * @param hasTitle 源文件是否包含表头
     * @return
     */
    LocalDataFileDetail uploadFile(MultipartFile file, boolean hasTitle);

    /**
     * 添加文件信息
     * @param req
     * @return
     */
    int add(LocalDataFileDetail req);

    /**
     * 删除文件元数据
     * @param metaDataId
     */
    int delete(String metaDataId);

    /**
     * 从数据节点下载源文件
     * @param response
     * @param metaDataId 源文件的metaDataId
     */
    void downLoad(HttpServletResponse response, String metaDataId);

    /**
     * 修改文件信息
     * @param req
     * @return
     */
    int update(LocalDataFileDetail req);

    /**
     *
     * @param metaDataId
     * @return
     */
    LocalDataFileDetail detail(String metaDataId);

    /**
     * 下架文件元数据
     * @param metaDataId
     * @return
     */
    int down(String metaDataId);

    /**
     * 上架文件元数据
     * @param metaDataId
     * @return
     */
    int up(String metaDataId);
}
