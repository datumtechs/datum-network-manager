package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.GlobalMetaDataColumn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GlobalMetaDataColumnMapper {


    /**
     * 根据metaDataId查询数据
     * @param metaDataId
     * @return
     */
    List<GlobalMetaDataColumn> selectByMetaDataId(String metaDataId);
}