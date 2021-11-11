package com.platon.metis.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author liushuyu
 * @Date 2021/7/9 10:33
 * @Version
 * @Desc
 */

@Data
@ApiModel
public class LocalDataPageResp {

    @ApiModelProperty(name = "id", value = "数据ID")
    private Integer id;

    @ApiModelProperty(name = "fileName", value = "元数据名称")
    private String fileName;

    @ApiModelProperty(name = "status", value = "元数据状态:1已发布，0未发布")
    private String status;

    @ApiModelProperty(name = "size", value = "数据大小")
    private Long size;

    @ApiModelProperty(name = "size", value = "元数据最近更新时间")
    private LocalDateTime recUpdateTime;

    @ApiModelProperty(name = "attendTaskCount", value = "参与任务数量")
    private Integer attendTaskCount;

    @ApiModelProperty(name = "metaDataId", value = "元数据ID,hash")
    private String metaDataId;
}
