package com.platon.datum.admin.service.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/8/8 14:39
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class ProposalMaterialContent {

    /**
     * 材料图片上传的ipfs地址
     */
    private String image;

    /**
     * 材料的描述
     */
    private String desc;

    /**
     * 公示备注信息
     */
    private String remark;
}

