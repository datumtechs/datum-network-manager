package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.Task;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "查询计算任务列表返回实体")
public class TaskDataPageResp {

    @ApiModelProperty(name = "id",value = "任务id")
    private String id;
    @ApiModelProperty(name = "taskName",value = "任务名称")
    private String taskName;
    @ApiModelProperty(name = "createAt",value = "任务发起时间 (时间戳)")
    private LocalDateTime createAt;
    @ApiModelProperty(name = "status", value = "任务状态 (pending: 等在中 (授权中); running: 计算中 (授权通过, 还未结束计算); failed: 失败 (计算结束); success: 成功 (计算结束))")
    private String status;
    @ApiModelProperty(name = "reviewed", value = "是否查看过")
    private Boolean reviewed;
    @ApiModelProperty(name = "role", value = "我在任务中的角色 (0: 未定义; 1: 发起方; 2: 数据提供方; 3: 计算参与方; 4: 结果提供方)")
    private Integer role;

    public static TaskDataPageResp convert(Task task){
        TaskDataPageResp resp = new TaskDataPageResp();
        BeanUtils.copyProperties(task,resp);
        return resp;
    }


}
