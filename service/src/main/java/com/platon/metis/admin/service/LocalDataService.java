package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.LocalDataFile;
import com.platon.metis.admin.dao.entity.LocalMetaData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface LocalDataService {

    /**
     * 获取本地组织数据分页列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<LocalMetaData> listMetaData(int pageNo, int pageSize, String keyword, String userAddress,int status);

    /**
     * 上传源文件到数据节点
     *
     * @param file     待上传的源文件
     * @param hasTitle 源文件是否包含表头
     * @return
     */
    LocalDataFile uploadFile(MultipartFile file, boolean hasTitle);

    /**
     * 添加文件信息
     *
     * @param localMetaData
     * @return
     */
    int addLocalMetaData(LocalMetaData localMetaData);


    /**
     * 删除文件元数据
     *
     * @param id
     */
    int delete(Integer id);

    /**
     * 从数据节点下载源文件
     *
     * @param response
     * @param id       元数据db id
     */
    void downLoad(HttpServletResponse response, Integer id);

    /**
     * 修改文件信息
     *
     * @param localMetaData
     * @return
     */
    int update(LocalMetaData localMetaData);


    /**
     * 下架文件元数据
     *
     * @param id
     * @return
     */
    void down(Integer id);

    /**
     * 上架文件元数据
     *
     * @param id
     * @return
     */
    int up(Integer id);

    /**
     * 查询指定的Id的resourceName是否在数据库中存在重复
     *
     * @param resourceName
     * @return
     */
    boolean isExistResourceName(String resourceName);

    /**
     * 根据关键字查询钱包自身的元数据列表摘要信息，状态为未绑定凭证Id的数据
     * @param keyword
     * @param userAddress
     * @return
     */
    List<LocalMetaData> listUnBindMetaData(String keyword, String userAddress);

//    void saveAndUp(LocalMetaData localMetaData);
}
