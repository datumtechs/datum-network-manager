package com.platon.datum.admin.service.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/8/7 17:40
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
public class VcMaterialContent {

    /**
     * 材料图片上传的ipfs地址
     */
    private String materialUrl;

    /**
     * 材料的描述
     */
    private String materialDesc;

}
