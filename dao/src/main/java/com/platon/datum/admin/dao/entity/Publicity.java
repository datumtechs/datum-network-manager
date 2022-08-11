package com.platon.datum.admin.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 公示信息
 * </p>
 *
 * @author chendai
 * @since 2022-07-08
 */
@Data
public class Publicity implements Serializable {


    /**
     * 公示的id, ipfs path
     */
    private String id;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 描述
     */
    private String describe;

    /**
     * 提案的附言
     */
    private String remark;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}
