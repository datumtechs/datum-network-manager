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
    //数据文件表自增id
    private Integer dataFileId;
    //元数据ID,hash
    private String metaDataId;
    //元数据名称
    private String metaDataName;
    //数据的状态 (created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)
    private String status;
    //数据是否为另存数据(true:是另存，false:非另存)
    /**
     * 场景：
     * 1、新数据提交----标记另存
     * 2、数据另存提交 ---标记另存
     * 3、数据撤销，并修改------标记另存
     */
    private Boolean hasOtherSave;
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