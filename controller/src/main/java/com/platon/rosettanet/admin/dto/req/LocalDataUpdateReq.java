package com.platon.rosettanet.admin.dto.req;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
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
@ApiModel
public class LocalDataUpdateReq {

    /**
     * 序号
     */
    @ApiModelProperty(value = "数据ID",required = true)
    @NotNull
    private Integer id;
    //数据描述
    @ApiModelProperty(value = "数据描述",required = true)
    private String remarks;

    //源文件列信息
    @ApiModelProperty(value = "源文件列信息",required = true)
    private List<LocalMetaDataColumn> localMetaDataColumnList = new ArrayList<>();
}
