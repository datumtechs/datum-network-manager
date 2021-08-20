package com.platon.rosettanet.admin.dto.resp.auth;

import com.platon.rosettanet.admin.dao.entity.LocalDataAuth;
import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import com.platon.rosettanet.admin.dao.enums.LocalDataFileStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 *授权数据列表
 */

@Data
@ApiModel(value = "授权数据列表响应")
public class LocalDataAuthPageResp {

    @ApiModelProperty(name = "id", value = "数据ID，序号")
    private Integer id;
    @ApiModelProperty(name = "metaDataId", value = "元数据Id")
    private String metaDataId;
    @ApiModelProperty(name = "fileName", value = "源文件名称")
    private String fileName;
    @ApiModelProperty(name = "resourceName", value = "数据名称")
    private String resourceName;
    @ApiModelProperty(name = "ownerIdentityId", value = "授权发起方身份标识")
    private String ownerIdentityId;
    @ApiModelProperty(name = "ownerIdentityName", value = "授权发起方名称")
    private String ownerIdentityName;
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
    //创建时间
    @ApiModelProperty(name = "recCreateTime", value = "创建时间,单位ms")
    private Long recCreateTime;
    //最后更新时间
    @ApiModelProperty(name = "recUpdateTime", value = "最后更新时间,单位ms")
    private Long recUpdateTime;



    public static LocalDataAuthPageResp from(LocalDataAuth localDataAuth){
        if(localDataAuth == null){
            return null;
        }
        LocalDataAuthPageResp localDataPageResp = new LocalDataAuthPageResp();
        BeanUtils.copyProperties(localDataAuth, localDataPageResp);
        localDataPageResp.setCreateAt(getTime(localDataAuth.getCreateAt()));
        localDataPageResp.setAuthAt(getTime(localDataAuth.getAuthAt()));
        localDataPageResp.setAuthValueStartAt(getTime(localDataAuth.getAuthValueStartAt()));
        localDataPageResp.setAuthValueEndAt(getTime(localDataAuth.getAuthValueEndAt()));
        localDataPageResp.setRecCreateTime(getTime(localDataAuth.getRecCreateTime()));
        localDataPageResp.setRecUpdateTime(getTime(localDataAuth.getRecUpdateTime()));
        //
        String fileName = (String) localDataAuth.getDynamicFields().get("fileName");
        String resourceName = (String) localDataAuth.getDynamicFields().get("resourceName");
        localDataPageResp.setFileName(fileName);
        localDataPageResp.setResourceName(resourceName);
        return localDataPageResp;
    }

    public static Long getTime(Date data){
        return Objects.isNull(data) ? null : data.getTime();
    }
}
