package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.entity.TaskEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZoneOffset;

@ApiModel(value = "任务事件日志列表实体对象")
@Data
public class TaskEventListResp {

    //主键
    @ApiModelProperty(name = "id", value = "主键id")
    private Integer id;
    //事件类型
    @ApiModelProperty(name = "eventType", value = "事件类型")
    private String eventType;
    //任务ID,hash
    @ApiModelProperty(name = "taskId", value = "任务ID,hash")
    private String taskId;
    //产生事件的组织身份信息id
    @ApiModelProperty(name = "identityId", value = "产生事件的组织身份信息id")
    private String identityId;
    //组织节点名称
    @ApiModelProperty(name = "nodeName", value = "组织节点名称")
    private String nodeName;
    //事件内容
    @ApiModelProperty(name = "eventContent", value = "事件内容")
    private String eventContent;
    //产生事件的时间
    @ApiModelProperty(name = "eventAt", value = "产生事件的时间，单位ms")
    private Long eventAt;


    public static TaskEventListResp convert(TaskEvent taskEvent){
        TaskEventListResp resp = new TaskEventListResp();
        resp.setId(taskEvent.getId());
        resp.setEventAt(taskEvent.getEventAt() == null ? null : taskEvent.getEventAt().toInstant(ZoneOffset.UTC).toEpochMilli());
        resp.setEventContent(taskEvent.getEventContent());
        resp.setEventType(taskEvent.getEventType());
        resp.setIdentityId(taskEvent.getIdentityId());
        resp.setNodeName(taskEvent.getDynamicFields().get("nodeName").toString());
        resp.setTaskId(taskEvent.getTaskId());
        //BeanUtils.copyProperties(taskEvent,resp);
        return resp;
    }





}
