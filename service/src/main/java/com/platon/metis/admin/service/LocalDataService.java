package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.LocalDataFileDetail;
import com.platon.metis.admin.dao.entity.LocalMetaData;
import com.platon.metis.admin.dao.entity.LocalMetaDataItem;
import com.platon.metis.admin.dao.entity.Task;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface LocalDataService {

    /**
     * 获取本地组织数据分页列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<LocalMetaDataItem> listDataFile(int pageNo, int pageSize, String keyword);

    /**
     * 获取本组织数据所参与的任务列表
     * @param pageNo
     * @param pageSize
     * @param metaDataId
     * @return
     */
    Page<Task> listDataJoinTask(int pageNo, int pageSize, String metaDataId, String keyword);


    /**
     * 上传源文件到数据节点
     * @param file 待上传的源文件
     * @param hasTitle 源文件是否包含表头
     * @return
     */
    LocalDataFileDetail uploadFile(MultipartFile file, boolean hasTitle);

    /**
     * 添加文件信息
     * @param detail
     * @return
     */
    int add(LocalDataFileDetail detail, LocalMetaData metaData);

    /**
     * 添加文件信息
     * @param detail
     * @return
     */
    int addAgain(LocalDataFileDetail detail, LocalMetaData metaData);

    /**
     * 删除文件元数据
     * @param id
     */
    int delete(Integer id);

    /**
     * 从数据节点下载源文件
     * @param response
     * @param id 文件id
     */
    void downLoad(HttpServletResponse response, Integer id);

    /**
     * 修改文件信息
     * @param req
     * @return
     */
    int update(LocalDataFileDetail req, LocalMetaData metaData);

    /**
     * 查询文件信息详情
     * @param id
     * @return
     */
    LocalDataFileDetail detail(Integer id);

    /**
     * 下架文件元数据
     * @param id
     * @return
     */
    int down(Integer id);

    /**
     * 上架文件元数据
     * @param id
     * @return
     */
    int up(Integer id);

    /**
     * 查询指定的Id的resourceName是否在数据库中存在重复
     * @param resourceName
     * @return
     */
    boolean isExistResourceName(String resourceName,Integer id);
}
