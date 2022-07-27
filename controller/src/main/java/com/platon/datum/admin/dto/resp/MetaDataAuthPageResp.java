package com.platon.datum.admin.dto.resp;

import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.entity.DataAuth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * 授权数据列表
 */

@Data
@ApiModel(value = "授权数据列表响应")
public class MetaDataAuthPageResp {
    @ApiModelProperty(name = "authId", value = "元数据授权申请Id")
    private String authId;
    @ApiModelProperty(name = "metaDataId", value = "元数据Id")
    private String metaDataId;
    @ApiModelProperty(name = "resourceName", value = "数据名称")
    private String resourceName;
    @ApiModelProperty(name = "applyUser", value = "发起任务的用户的信息 (task是属于用户的)")
    private String applyUser;
    @ApiModelProperty(name = "userType", value = "发起任务用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)")
    private Integer userType;
    @ApiModelProperty(name = "authType", value = "授权方式(0：未定义，1：时间，2：次数)")
    private Integer authType;
    @ApiModelProperty(name = "authValueAmount", value = "授权值(以授权次数)，auth_type = 2使用此字段")
    private Integer authValueAmount;
    @ApiModelProperty(name = "authValueStartAt", value = "授权值结束时间，auth_type = 1使用此字段")
    private Long authValueStartAt;
    @ApiModelProperty(name = "authValueEndAt", value = "授权值结束时间，auth_type = 1使用此字段")
    private Long authValueEndAt;
    @ApiModelProperty(name = "createAt", value = "授权申请发起时间")
    private Long createAt;
    @ApiModelProperty(name = "authAt", value = "授权数据时间")
    private Long authAt;
    @ApiModelProperty(name = "status", value = "授权数据状态：0：等待授权审核，1:同意， 2:拒绝")
    private Integer status;
    //创建时间
    @ApiModelProperty(name = "recCreateTime", value = "创建时间,单位ms")
    private Long recCreateTime;
    //最后更新时间
    @ApiModelProperty(name = "recUpdateTime", value = "最后更新时间,单位ms")
    private Long recUpdateTime;


    public static MetaDataAuthPageResp from(DataAuth dataAuth) {
        if (dataAuth == null) {
            return null;
        }
        MetaDataAuthPageResp localDataPageResp = new MetaDataAuthPageResp();
        BeanUtils.copyProperties(dataAuth, localDataPageResp);
        localDataPageResp.setCreateAt(getTime(dataAuth.getCreateAt()));
        localDataPageResp.setAuthAt(getTime(dataAuth.getAuthAt()));
        localDataPageResp.setAuthValueStartAt(getTime(dataAuth.getAuthValueStartAt()));
        localDataPageResp.setAuthValueEndAt(getTime(dataAuth.getAuthValueEndAt()));
        localDataPageResp.setRecCreateTime(getTime(dataAuth.getRecCreateTime()));
        localDataPageResp.setRecUpdateTime(getTime(dataAuth.getRecUpdateTime()));
        //
        String resourceName = (String) dataAuth.getDynamicFields().get("resourceName");
        localDataPageResp.setResourceName(resourceName);
        return localDataPageResp;
    }

    private static long getTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return 0;
        }
        return LocalDateTimeUtil.getTimestamp(localDateTime);
    }
}