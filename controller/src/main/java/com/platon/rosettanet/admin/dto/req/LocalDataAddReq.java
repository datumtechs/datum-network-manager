package com.platon.rosettanet.admin.dto.req;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
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

    //数据库id
    @ApiModelProperty(value = "数据库id",required = true)
    @NotNull
    private Integer id;
    //资源名称
    @ApiModelProperty(value = "资源名称",required = true)
    private String resourceName;
    //数据描述
    @ApiModelProperty(value = "数据描述",required = true)
    private String remarks;
    //源文件列信息
    @ApiModelProperty(value = "源文件列信息",required = true)
    private List<LocalMetaDataColumn> localMetaDataColumnList = new ArrayList<>();
}
