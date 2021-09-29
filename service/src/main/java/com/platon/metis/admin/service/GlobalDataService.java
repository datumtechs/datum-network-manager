package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.GlobalDataFile;

public interface GlobalDataService {

    /**
     * 获取全网组织数据分页列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<GlobalDataFile> listDataFile(int pageNo, int pageSize, String keyword);

    /**
     * 获取数据详情
     * @param id
     * @return
     */
    GlobalDataFile findGlobalDataFile(Integer id);
}
