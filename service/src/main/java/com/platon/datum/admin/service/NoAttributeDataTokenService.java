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


    Page<DataToken> page(Integer pageNumber, Integer pageSize, String keyword, String address);

    Integer publish(DataToken dataToken);

    void up(DataToken dataToken);

    DataToken getDataTokenById(Integer id);

    void updateToPrinceSuccess(Integer dataTokenId, String currentUserAddress);

    void updateFee(Integer dataTokenId, String ciphertextFee, String plaintextFee, String sign, String currentUserAddress);

    void bindMetaData(Integer dataTokenId, String sign, String currentUserAddress);

}
