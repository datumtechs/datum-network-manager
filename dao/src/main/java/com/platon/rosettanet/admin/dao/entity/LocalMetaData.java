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
    //所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业
    private Integer industry;
    //创建时间
    private Date recCreateTime;
    //更新时间
    private Date recUpdateTime;


}