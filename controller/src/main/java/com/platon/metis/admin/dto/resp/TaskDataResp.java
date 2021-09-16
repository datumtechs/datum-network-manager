package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.entity.TaskStatistics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@ApiModel(value = "查询计算任务列表返回实体")
@Data
public class TaskDataResp {
    @ApiModelProperty(name = "list", value = "任务列表数据")
    List<TaskDataPageResp> list;
    @ApiModelProperty(name = "taskStatisticsRes", value = "任务信息数据统计")
    TaskStatisticsRes taskStatisticsRes;


    public static TaskDataResp from(List<TaskDataPageResp> list, TaskStatistics taskStatistics){

        TaskStatisticsRes res = new TaskStatisticsRes();
        BeanUtils.copyProperties(taskStatistics, res);

        TaskDataResp taskDataResp = new TaskDataResp();
        taskDataResp.setList(list);
        taskDataResp.setTaskStatisticsRes(res);
        return taskDataResp;
    }


    @Data
    public static class TaskStatisticsRes {
        @ApiModelProperty(name = "totalTaskCount", value = "任务总数")
        private int totalTaskCount;
        @ApiModelProperty(name = "taskSuccessCount", value = "任务成功数量")
        private int taskSuccessCount;
        @ApiModelProperty(name = "taskFailedCount", value = "任务失败数量")
        private int taskFailedCount;
        @ApiModelProperty(name = "taskPendingCount", value = "任务等待中数量")
        private int taskPendingCount;
        @ApiModelProperty(name = "taskRunningCount", value = "任务计算中数量")
        private int taskRunningCount;
        @ApiModelProperty(name = "ownerCount", value = "任务发起方数量")
        private int ownerCount;
        @ApiModelProperty(name = "dataSupplierCount", value = "数据提供方数量")
        private int dataSupplierCount;
        @ApiModelProperty(name = "powerSupplierCount", value = "算力提供方数量")
        private int powerSupplierCount;
        @ApiModelProperty(name = "receiverCount", value = "结果接收方数量")
        private int receiverCount;
        @ApiModelProperty(name = "algoSupplierCount", value = "算法提供方数量")
        private int algoSupplierCount;
    }


}
