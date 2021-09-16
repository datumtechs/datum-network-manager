package com.platon.metis.admin.grpc.entity;

import com.platon.metis.admin.dao.entity.TaskEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter
@Getter
@ToString
public class TaskEventDataResp extends CommonResp{
    private List<TaskEvent> taskEventList;
}
