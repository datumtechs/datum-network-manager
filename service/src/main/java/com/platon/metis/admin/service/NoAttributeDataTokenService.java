package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.DataToken;

/**
 * @Author liushuyu
 * @Date 2022/3/24 11:57
 * @Version
 * @Desc
 */
public interface NoAttributeDataTokenService {


    Page<DataToken> page(Integer pageNumber, Integer pageSize, int status);

    void publish(DataToken dataToken);

    void up(DataToken dataToken);
}
