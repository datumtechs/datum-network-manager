package com.platon.metis.admin.grpc.entity.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/5/5 14:33
 * @Version
 * @Desc
 * 2: dir格式 (目录)
 * "{
 * 	"partyId": "p0",
 * 	"metadataId": "metadata:0xf7396b9a6be9c20...c54880c2d",
 * 	"metadataName": "aaa",
 * 	"inputType": 3 // 输入数据的类型，0:unknown, 1:origin_data, 2:psi_output, 3:model
 * }"
 */

@Getter
@Setter
@ToString
public class DataPolicyOption2 {

    private String partyId;
    private String metadataId;
    private String metadataName;
    private Integer inputType;
}
