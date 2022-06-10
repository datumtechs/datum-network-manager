package com.platon.datum.admin.grpc.entity.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/5/5 14:33
 * @Version
 * @Desc 30001: csv格式原始数据 (基于任务的结果文件)
 * "{
 * "partyId": "p0",
 * "taskId": "task:0x43b3d8c65b877adfd05a77dc6b3bb1ad27e4727edbccb3cc76ffd51f78794479",
 * "inputType": 1, // 输入数据的类型，0:unknown, 1:origin_data, 2:psi_output, 3:model
 * "keyColumnName": "id",
 * "selectedColumnNames": ["name", "age", "point"]
 */

@Getter
@Setter
@ToString
public class DataPolicyOption30001 {

    private String partyId;
    private String taskId;
    private Integer inputType;
    private String keyColumnName;
    private List<String> selectedColumnNames = new ArrayList<>();
}
