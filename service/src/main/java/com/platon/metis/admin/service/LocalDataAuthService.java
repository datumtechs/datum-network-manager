package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.dao.entity.LocalDataAuthDetail;

import java.util.List;

public interface LocalDataAuthService {

    /**
     * 获取数据授权列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<LocalDataAuth> listLocalDataAuth(int pageNo, int pageSize, int status, String keyword);

    List<LocalDataAuth> listAll();

    /**
     * 获取已授权的数据数量（所有的同意 + 拒绝授权数据）
     * @return
     */
    int selectFinishAuthCount();

    /**
     * 获取未授权的数据数量（所有的待授权的数据）
     * @return
     */
    int selectUnfinishAuthCount();

    /**
     * 查询数据授权详情
     * @param authId
     * @return
     */
    LocalDataAuthDetail detail(String authId);

    /**
     * 同意授权数据
     * @param authId
     * @return
     */
    void agreeAuth(String authId);

    /**
     * 拒绝授权数据
     * @param authId
     * @return
     */
    void refuseAuth(String authId);


}
