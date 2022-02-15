package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 本组织元数据文件表，文件的元数据信息
 */
@Data
@ApiModel
public class LocalMetaData extends BaseDomain {
    //序号,
    @ApiModelProperty(name = "id", value = "PK,记录ID")
    private Integer id;

    @ApiModelProperty(name = "fileId", value = "源文件ID")
    private String fileId;

    //元数据ID,hash,元数据发布以后才有此值
    @ApiModelProperty(name = "metaDataId", value = "元数据ID,元数据发布后得到此唯一值")
    private String metaDataId;
    //元数据名称
    @ApiModelProperty(name = "metaDataName", value = "元数据名称")
    private String metaDataName;
    //
    @ApiModelProperty(name = "status", value = "元数据的状态 (0: 未知; 1: 未发布; 2: 已发布; 3: 已撤销;4:已删除;5: 发布中; 6：撤回中)")
    private Integer status;
    //数据是否为另存数据(true:是另存，false:非另存)
    /**
     * 场景：
     * 1、新数据提交----标记另存
     * 2、数据另存提交 ---标记另存
     * 3、数据撤销，并修改------标记另存
     */
    private Boolean hasOtherSave;
    //
    @ApiModelProperty(name = "publishTime", value = "元数据发布时间")
    private LocalDateTime publishTime;
    //数据描述
    @ApiModelProperty(name = "remarks", value = "元数据描述")
    private String remarks;

    //所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业
    @ApiModelProperty(name = "industry", value = "元数据所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业")
    private Integer industry;
    //创建时间
    private LocalDateTime recCreateTime;
    //更新时间
    private LocalDateTime recUpdateTime;

    @ApiModelProperty(name = "localMetaDataColumnList", value = "元数据的字段定义")
    List<LocalMetaDataColumn> localMetaDataColumnList;

    public void addLocalMetaDataColumn(LocalMetaDataColumn localMetaDataColumn){
        if(localMetaDataColumnList==null){
            localMetaDataColumnList = new ArrayList<>();
        }
        localMetaDataColumnList.add(localMetaDataColumn);
    }
}