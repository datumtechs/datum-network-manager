package com.platon.datum.admin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author liushuyu
 * @Date 2022/7/22 18:31
 * @Version
 * @Desc
 */
public interface IpfsOpService {

    /**
     * 保存文件
     *
     * @return
     */
    String saveFile(MultipartFile file);


    /**
     * 保存json
     *
     * @return
     */
    String saveJson(Object content);

    String queryJson(String url);
}
