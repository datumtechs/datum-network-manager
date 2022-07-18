package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.AttributeDataToken;
import com.platon.datum.admin.dao.entity.DataToken;

/**
 * @Author liushuyu
 * @Date 2022/3/24 11:57
 * @Version
 * @Desc
 */
public interface AttributeDataTokenService {


    Page<AttributeDataToken> page(Integer pageNumber, Integer pageSize, String keyword, String currentUserAddress);

    Integer publish(AttributeDataToken dataToken);

    AttributeDataToken getDataTokenById(Integer dataTokenId,String currentUserAddress);

    void updateStatus(Integer dataTokenId, int status, String currentUserAddress);
}
