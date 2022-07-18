package com.platon.datum.admin.dto.resp;

import com.platon.datum.admin.dao.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 *
 * 查看授权数据详情
 */

@Data
@ApiModel(value = "查看授权数据详情返回")
public class MetaDataAuthDetailResp {

    @ApiModelProperty(name = "authInfo", value = "授权申请信息")
    DataAuth authInfo;
    @ApiModelProperty(name = "basicDataInfo", value = "基本信息")
    MetaData basicDataInfo;
    @ApiModelProperty(name = "metaDataColumnList", value = "字段信息")
    List<MetaDataColumn> metaDataColumnList;

    public static MetaDataAuthDetailResp from(DataAuthDetail detail){
        com.platon.datum.admin.dao.entity.DataAuth dataAuth = detail.getDataAuth();
        DataFile dataFile = detail.getDataFile();
        com.platon.datum.admin.dao.entity.MetaData metaData = detail.getMetaData();
        List<MetaDataColumn> metaDataColumnList = detail.getMetaDataColumnList();

        //授权申请信息
        DataAuth authInfo = new DataAuth();
        BeanUtils.copyProperties(dataAuth, authInfo);
        authInfo.setFileName(dataFile.getFileName());
        authInfo.setResourceName(metaData.getMetaDataName());
        authInfo.setCreateAt(getTime(dataAuth.getCreateAt()));
        authInfo.setAuthAt(getTime(dataAuth.getAuthAt()));
        authInfo.setAuthValueStartAt(getTime(dataAuth.getAuthValueStartAt()));
        authInfo.setAuthValueEndAt(getTime(dataAuth.getAuthValueEndAt()));
        authInfo.setRecCreateTime(getTime(dataAuth.getRecCreateTime()));
        authInfo.setRecUpdateTime(getTime(dataAuth.getRecUpdateTime()));

        //基本信息
        MetaData basicDataInfo = new MetaData();
        BeanUtils.copyProperties(metaData,basicDataInfo);
        basicDataInfo.setFileSize(dataFile.getSize());
        basicDataInfo.setDataColumns(dataFile.getColumns());
        basicDataInfo.setDataRows(dataFile.getRows());
        basicDataInfo.setPublishTime(getTime(metaData.getPublishTime()));

        MetaDataAuthDetailResp resp = new MetaDataAuthDetailResp();
        resp.setAuthInfo(authInfo);
        resp.setBasicDataInfo(basicDataInfo);
        resp.setMetaDataColumnList(metaDataColumnList);
        return resp;
    }

    private static long getTime(LocalDateTime localDateTime){
        if(localDateTime == null){
            return 0;
        }
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }


    @Data
    public static class DataAuth{
        @ApiModelProperty(name = "id", value = "序号")
        private Integer id;
        @ApiModelProperty(name = "authId", value = "元数据授权申请Id")
        private String authId;
        @ApiModelProperty(name = "metaDataId", value = "元数据ID")
        private String metaDataId;
        @ApiModelProperty(name = "applyUser", value = "发起任务的用户的信息 (task是属于用户的)")
        private String applyUser;
        @ApiModelProperty(name = "userType", value = "发起任务用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)")
        private Integer userType;
        @ApiModelProperty(name = "fileName", value = "源文件名称")
        private String fileName;
        @ApiModelProperty(name = "resourceName", value = "数据名称")
        private String resourceName;
        @ApiModelProperty(name = "authType", value = "授权方式(1：时间，2：次数)")
        private Integer authType;
        @ApiModelProperty(name = "authValueAmount", value = "授权值(以授权次数)")
        private Integer authValueAmount;
        @ApiModelProperty(name = "authValueStartAt", value = "授权值开始时间")
        private Long authValueStartAt;
        @ApiModelProperty(name = "authValueEndAt", value = "授权值结束时间")
        private Long authValueEndAt;
        @ApiModelProperty(name = "createAt", value = "授权申请发起时间")
        private Long createAt;
        @ApiModelProperty(name = "authAt", value = "授权数据时间")
        private Long authAt;
        @ApiModelProperty(name = "status", value = "授权数据状态：0：等待授权审核，1:同意， 2:拒绝")
        private Integer status;
        @ApiModelProperty(name = "recCreateTime", value = "创建时间")
        private Long recCreateTime;
        @ApiModelProperty(name = "recUpdateTime", value = "最后更新时间")
        private Long recUpdateTime;
    }


    @Data
    public static class MetaData {
        @ApiModelProperty(name = "publishTime", value = "元数据发布时间")
        private Long publishTime;
        @ApiModelProperty(name = "fileSize", value = "数据文件大小(字节)")
        private Long fileSize;
        @ApiModelProperty(name = "dataRows", value = "数据条数")
        private Integer dataRows;
        @ApiModelProperty(name = "dataColumns", value = "字段数")
        private Integer dataColumns;
        @ApiModelProperty(name = "industry", value = "所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业")
        private Integer industry;
        @ApiModelProperty(name = "remarks", value = "数据描述")
        private String remarks;
    }






}
