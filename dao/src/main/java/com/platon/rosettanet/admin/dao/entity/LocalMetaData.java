package com.platon.rosettanet.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 本组织元数据文件表，文件的元数据信息
 */
@Data
public class LocalMetaData {
    //序号
    private Integer id;
    //源文件ID，上传文件成功后返回源文件ID
    private String fileId;
    //元数据ID,hash
    private String metaDataId;
    //文件大小(字节)
    private Long size;
    //数据的状态 (created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)
    private String status;
    //元数据发布时间
    private Date publishTime;
    //数据描述
    private String remarks;
    //创建时间
    private Date recCreateTime;
    //更新时间
    private Date recUpdateTime;


}