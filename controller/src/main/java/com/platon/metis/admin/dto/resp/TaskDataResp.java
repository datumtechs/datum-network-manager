package com.platon.metis.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "查询计算任务列表返回实体")
@Data
public class TaskDataResp {
    @ApiModelProperty(name = "list", value = "任务列表数据")
    List<TaskDataPageResp> list;


    public static TaskDataResp from(List<TaskDataPageResp> list){
        TaskDataResp taskDataResp = new TaskDataResp();
        taskDataResp.setList(list);
        return taskDataResp;
    }
}
