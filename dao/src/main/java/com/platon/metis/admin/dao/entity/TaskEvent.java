package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@ApiModel(value = "任务事件")
public class TaskEvent extends BaseDomain {
    //主键
    @ApiModelProperty(name = "id", value = "事件记录id")
    private Integer id;

    //任务ID,hash
    @ApiModelProperty(name = "taskId", value = "任务ID")
    private String taskId;

    //事件类型
    @ApiModelProperty(name = "eventType", value = "事件类型")
    private String eventType;

    //产生事件的组织身份信息id
    @ApiModelProperty(name = "identityId", value = "产生事件的组织身份ID")
    private String identityId;
    // 产生事件的partyId (单个组织可以担任任务的多个party, 区分是哪一方产生的event)

    @ApiModelProperty(name = "partyId", value = "产生事件的组织在任务中的party ID")
    private String partyId;
    //事件内容
    @ApiModelProperty(name = "eventContent", value = "事件内容")
    private String eventContent;
    //产生事件的时间
    @ApiModelProperty(name = "eventAt", value = "产生事件的时间")
    private LocalDateTime eventAt;
}
