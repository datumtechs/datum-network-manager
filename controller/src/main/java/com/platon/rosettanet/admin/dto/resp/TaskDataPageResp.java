package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.Task;
import com.platon.rosettanet.admin.dao.enums.RoleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Data
@ApiModel(value = "查询计算任务列表返回实体")
public class TaskDataPageResp {

    @ApiModelProperty(name = "id",value = "序号")
    private Integer id;
    @ApiModelProperty(name = "taskId",value = "任务id")
    private String taskId;
    @ApiModelProperty(name = "taskName",value = "任务名称")
    private String taskName;
    @ApiModelProperty(name = "createAt",value = "任务发起时间 (时间戳),单位ms")
    private Long createAt;
    @ApiModelProperty(name = "status", value = "任务状态 (pending: 等在中; failed: 失败 (计算结束); success: 成功 (计算结束))")
    private String status;
    @ApiModelProperty(name = "reviewed", value = "是否查看过")
    private Boolean reviewed;
    @ApiModelProperty(name = "role", value = "我在任务中的角色 (0: 未定义; 1: 发起方; 2: 数据提供方; 3: 计算参与方; 4: 结果提供方)")
    private Integer role;


    public static TaskDataPageResp convert(Task task){
        TaskDataPageResp resp = new TaskDataPageResp();
        resp.setId(task.getId());
        resp.setTaskId(task.getTaskId());
        resp.setTaskName(task.getTaskName());
        resp.setStatus(task.getStatus());
        resp.setReviewed(task.getReviewed());
        resp.setRole(getRole(task.getRole()));
        resp.setCreateAt(task.getCreateAt() == null ? null :task.getCreateAt().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        //BeanUtils.copyProperties(task,resp);
        return resp;
    }



    /**
     *
     * @param role
     * @return
     */
    public static int getRole(String role){

        if(RoleEnum.OWNER.getMessage().equals(role)){
            return RoleEnum.OWNER.getCode();
        }else if(RoleEnum.DATASUPPLIER.getMessage().equals(role)){
            return RoleEnum.DATASUPPLIER.getCode();
        }else if(RoleEnum.POWERSUPPLIER.getMessage().equals(role)){
            return RoleEnum.POWERSUPPLIER.getCode();
        }else if(RoleEnum.RECEIVER.getMessage().equals(role)){
            return RoleEnum.RECEIVER.getCode();
        }else {
            return RoleEnum.UNDEFINED.getCode();
        }
    }


}
