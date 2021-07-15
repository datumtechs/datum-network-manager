package com.platon.rosettanet.admin.dto.req;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/15 11:57
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class LocalDataUpdateReq {

    /**
     * 序号
     */
    private Integer id;
    //组织身份ID
    private String identityId;
    //源文件ID，上传文件成功后返回源文件ID
    private String fileId;
    //文件名称
    private String fileName;
    //文件存储路径
    private String filePath;
    //文件后缀/类型, csv
    private String fileType;
    //资源名称
    private String resourceName;
    //文件大小(字节)
    private Long size;
    //数据行数(不算title)
    private Long rows;
    //数据列数
    private Integer columns;
    //是否带标题
    private Boolean hasTitle;
    //数据描述
    private String remarks;
    //数据的状态 (created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)
    private String status;
    //元数据ID,hash
    private String metaDataId;
    //创建时间
    private Date recCreateTime;
    //最后更新时间
    private Date recUpdateTime;

    //源文件列信息
    private List<LocalMetaDataColumn> localMetaDataColumnList = new ArrayList<>();
}
