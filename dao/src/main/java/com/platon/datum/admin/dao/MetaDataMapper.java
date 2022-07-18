package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.MetaData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MetaDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MetaData record);

    /**
     * 关键字查询未发布或者已发布数据凭证的元数据
     * @param keyword
     * @param userAddress
     * @param status
     * @return
     */
    List<MetaData> listMetaData(@Param("keyword") String keyword, @Param("userAddress") String userAddress, @Param("status") int status);

    MetaData selectByPrimaryKey(Integer id);

    MetaData findWithTaskCount(Integer id);

    MetaData selectByMetaDataId(String metaDataId);

    MetaData checkResourceName(@Param("resourceName") String resourceName, @Param("address") String address);

    int updateByPrimaryKey(MetaData record);

    /**
     * 根据metaDataId, 更新 status, publish_time, rec_update_time,attribute_token_address,attribute_data_token_address
     *
     * @param metaDataList
     */
    void updateByMetaDataIdBatch(@Param("metaDataList") List<MetaData> metaDataList);

    int updateStatusById(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 关键字查询出未发布无属性凭证的元数据
     * @param keyword
     * @param userAddress
     * @return
     */
    List<MetaData> listMetaDataUnPublishDataToken(@Param("keyword") String keyword, @Param("userAddress") String userAddress);

    /**
     * 关键字查询出未发布有属性凭证的元数据
     * @param keyword
     * @param userAddress
     * @return
     */
    List<MetaData> listMetaDataUnPublishAttributeDataToken(@Param("keyword") String keyword, @Param("userAddress") String userAddress);

    List<MetaData> selectByStatus(Integer status);

    /**
     * 查询出未绑定无属性凭证的元数据
     */
    List<MetaData> selectUnBindDataToken();

    /**
     * 查询出未绑定有属性凭证的元数据
     */
    List<MetaData> selectUnBindAttributeDataToken();

}