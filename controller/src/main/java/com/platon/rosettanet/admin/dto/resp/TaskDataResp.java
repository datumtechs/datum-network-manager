package com.platon.rosettanet.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@ApiModel(value = "查询计算任务列表返回实体")
@Data
public class TaskDataResp {
    @ApiModelProperty(name = "list", value = "任务列表数据")
    List<TaskDataPageResp> list;
    @ApiModelProperty(name = "countData", value = "任务数量")
    Map countData;
}
