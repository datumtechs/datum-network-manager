package com.platon.datum.admin.grpc.entity;

import com.platon.datum.admin.dao.entity.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter
@Getter
@ToString
public class TaskDataResp extends CommonResp{
    private List<Task> taskList;
}
