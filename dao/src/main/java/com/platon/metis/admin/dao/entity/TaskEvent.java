package com.platon.metis.admin.dao.entity;

import com.platon.metis.admin.dao.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Setter
@Getter
@ToString
public class TaskEvent extends BaseDomain {
    //主键
    private Integer id;
    //事件类型
    private String eventType;
    //任务ID,hash
    private String taskId;
    //产生事件的组织身份信息id
    private String identityId;
    // 产生事件的partyId (单个组织可以担任任务的多个party, 区分是哪一方产生的event)
    private String partyId;
    //事件内容
    private String eventContent;
    //产生事件的时间
    private LocalDateTime eventAt;
}
