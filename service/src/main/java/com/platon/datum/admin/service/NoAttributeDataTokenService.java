package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.DataToken;

/**
 * @Author liushuyu
 * @Date 2022/3/24 11:57
 * @Version
 * @Desc
 */
public interface NoAttributeDataTokenService {


    Page<DataToken> page(Integer pageNumber, Integer pageSize, int status, String address);

    Integer publish(DataToken dataToken);

    void up(DataToken dataToken);

    DataToken getDataTokenById(Integer dataTokenId);

    void updateStatus(Integer dataTokenId, int status, String currentUserAddress);
}
