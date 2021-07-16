package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.GlobalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.GlobalMetaDataColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/16 12:31
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class GlobalDataDetailResp  implements Serializable {
    /**
     * 序号
     */
    private Integer id;

    /**
     * 组织身份ID
     */
    private String identityId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 源文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文件后缀/类型, csv
     */
    private String fileType;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 文件大小(字节)
     */
    private Long size;

    /**
     * 数据行数(不算title)
     */
    private Long rows;

    /**
     * 数据列数
     */
    private Integer columns;

    /**
     * 是否带标题
     */
    private Byte hasTitle;

    /**
     * 数据描述
     */
    private String remarks;

    /**
     * 数据的状态 (created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)
     */
    private String status;

    /**
     * 元数据ID,hash
     */
    private String metaDataId;

    /**
     * 创建时间
     */
    private Date recCreateTime;

    /**
     * 最后更新时间
     */
    private Date recUpdateTime;

    private static final long serialVersionUID = 1L;

    //源文件列信息
    private List<GlobalMetaDataColumn> metaDataColumnList = new ArrayList<>();

    public static GlobalDataDetailResp from(GlobalDataFileDetail detail){
        GlobalDataDetailResp resp = new GlobalDataDetailResp();
        BeanUtils.copyProperties(detail,resp);
        return resp;
    }
}
