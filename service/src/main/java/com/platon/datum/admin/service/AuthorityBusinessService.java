package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.AuthorityBusiness;

/**
 * @Author liushuyu
 * @Date 2022/7/22 15:24
 * @Version
 * @Desc
 */
public interface AuthorityBusinessService {
    int getTodoCount(String identityId);

    Page<AuthorityBusiness> getTodoList(Integer pageNumber, Integer pageSize, String keyword);

    AuthorityBusiness getDetail(int id);

    void processTodo(int id, int result, String remark);

    Page<AuthorityBusiness> getDoneList(Integer pageNumber, Integer pageSize, String keyword);

}
