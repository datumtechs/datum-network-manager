package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * 本组织数据列表对象
 */
@Data
public class LocalMetaDataItem {
    //序号
    private Integer id;
    //元数据ID,hash
    private String metaDataId;
    //元数据名称
    private String metaDataName;
    //数据的状态 (created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)
    private String status;
    //文件大小
    private long size;
    //更新时间
    private Date recUpdateTime;
    //参与任务数量
    private int dataJoinTaskCount;


}