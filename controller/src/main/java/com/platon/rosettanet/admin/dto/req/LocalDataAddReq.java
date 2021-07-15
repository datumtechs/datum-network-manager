package com.platon.rosettanet.admin.dto.req;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/15 12:00
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class LocalDataAddReq {

    //资源名称
    @ApiModelProperty(value = "资源名称",required = true)
    private String resourceName;
    //数据描述
    @ApiModelProperty(value = "数据描述",required = true)
    private String remarks;
    //元数据ID,hash
    @ApiModelProperty(value = "元数据ID",required = true)
    private String metaDataId;
    //源文件列信息
    @ApiModelProperty(value = "源文件列信息",required = true)
    private List<LocalMetaDataColumn> localMetaDataColumnList = new ArrayList<>();
}
