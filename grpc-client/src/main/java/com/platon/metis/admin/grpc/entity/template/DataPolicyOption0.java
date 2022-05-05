package com.platon.metis.admin.grpc.entity.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/5/5 14:33
 * @Version
 * @Desc
 *
 * 0: 未知原始数据格式 (一般只有根据任务的结果文件由系统默认生成的元数据才会是这种格式)
 * "{
 * 	"partyId": "p0",
 * 	"metadataId": "metadata:0x2843e8103c...6c537c7",
 * 	"metadataName": "bbb",
 * 	"inputType": 3 // 输入数据的类型，0:unknown, 1:origin_data, 2:psi_output, 3:model
 * }"
 */

@Getter
@Setter
@ToString
public class DataPolicyOption0 {

    private String partyId;
    private String metadataId;
    private String metadataName;
    private Integer inputType;
}
