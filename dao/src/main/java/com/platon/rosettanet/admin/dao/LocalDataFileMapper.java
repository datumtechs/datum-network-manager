package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalDataFileMapper {

    /**
     * 获取所有本组织文件信息
     * @param keyword 关键字搜索，暂时按名称搜索，如果为空则返回所有
     * @return
     */
    List<LocalDataFile> listDataFile(String keyword);

    /**
     * 插入不为空的字段
     * @param localDataFile
     * @return
     */
    int insertSelective(LocalDataFile localDataFile);

    /**
     * 插入数据
     * @param localDataFile
     * @return
     */
    int insert(LocalDataFile localDataFile);

    /**
     *根据metaDataId进行选择性更新
     */
    int updateByFileIdSelective(LocalDataFile localDataFile);

    /**
     * 根据Id查询出指定的数据
     * @param id
     * @return
     */
    LocalDataFile selectById(Integer id);

    /**
     * 根据id删除指定的数据,released的数据不可删除
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     *根据Id进行选择性更新
     */
    int updateByIdSelective(LocalDataFile localDataFile);

    /**
     * 排除指定metaDataId之后，根据resourceName查询数据文件
     * @param resourceName
     * @return
     */
    LocalDataFile selectByResourceName(@Param("resourceName") String resourceName, @Param("id") Integer id);

    /**
     *
     * @param fileId
     * @return
     */
    LocalDataFile selectByFileId(@Param("fileId") String fileId);


}