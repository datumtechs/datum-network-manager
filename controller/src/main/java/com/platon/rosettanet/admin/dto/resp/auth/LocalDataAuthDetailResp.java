package com.platon.rosettanet.admin.dto.resp.auth;

import com.platon.rosettanet.admin.dao.BaseDomain;
import com.platon.rosettanet.admin.dao.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 查看授权数据详情
 */

@Data
@ApiModel(value = "查看授权数据详情返回")
public class LocalDataAuthDetailResp {

    @ApiModelProperty(name = "authInfo", value = "授权申请信息")
    DataAuth authInfo;
    @ApiModelProperty(name = "basicDataInfo", value = "基本信息")
    MetaData basicDataInfo;
    @ApiModelProperty(name = "localMetaDataColumnList", value = "字段信息")
    List<LocalMetaDataColumn> localMetaDataColumnList;

    public static LocalDataAuthDetailResp from(LocalDataAuthDetail detail){
        LocalDataAuth dataAuth = detail.getLocalDataAuth();
        LocalDataFile dataFile = detail.getLocalDataFile();
        LocalMetaData metaData = detail.getLocalMetaData();
        List<LocalMetaDataColumn> metaDataColumnList = detail.getLocalMetaDataColumnList();

        //授权申请信息
        DataAuth authInfo = new DataAuth();
        BeanUtils.copyProperties(dataAuth, authInfo);
        authInfo.setFileName(dataFile.getFileName());

        //基本信息
        MetaData basicDataInfo = new MetaData();
        BeanUtils.copyProperties(metaData,basicDataInfo);
        basicDataInfo.setFileSize(dataFile.getSize());
        basicDataInfo.setDataColumns(dataFile.getColumns());
        basicDataInfo.setDataRows(dataFile.getRows());

        LocalDataAuthDetailResp resp = new LocalDataAuthDetailResp();
        resp.setAuthInfo(authInfo);
        resp.setBasicDataInfo(basicDataInfo);
        resp.setLocalMetaDataColumnList(metaDataColumnList);
        return resp;
    }


    @Data
    public static class DataAuth{
        @ApiModelProperty(name = "id", value = "序号")
        private Integer id;
        @ApiModelProperty(name = "metaDataId", value = "元数据ID")
        private String metaDataId;
        @ApiModelProperty(name = "ownerIdentityId", value = "授权发起方身份标识")
        private String ownerIdentityId;
        @ApiModelProperty(name = "ownerIdentityName", value = "授权发起方名称")
        private String ownerIdentityName;
        @ApiModelProperty(name = "fileName", value = "数据名称")
        private String fileName;
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
        @ApiModelProperty(name = "status", value = "授权数据状态：1:同意， 2:拒绝 ，3:待授权")
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
        private Long dataRows;
        @ApiModelProperty(name = "dataColumns", value = "字段数")
        private Integer dataColumns;
        @ApiModelProperty(name = "remarks", value = "数据描述")
        private String remarks;
    }






}
