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
 * @Desc
 * 1: csv格式原始数据
 * "{
 * 	"partyId": "p0",
 * 	"metadataId": "metadata:0xf7396b9a6be9c20...c54880c2d",
 * 	"metadataName": "aaa",
 * 	"inputType": 1, // 输入数据的类型，0:unknown, 1:origin_data, 2:psi_output, 3:model
 * 	"keyColumn": 1,
 * 	"selectedColumns": [1, 2, 3]
 * }"
 */

@Getter
@Setter
@ToString
public class DataPolicyOption1 extends BaseDataPolicyOption{

    private String partyId;
    private String metadataId;
    private String metadataName;
    private Integer inputType;

    private Integer keyColumn;
    private List<Integer> selectedColumns = new ArrayList<>();

    @Override
    public Object[] getParameterNameArray() {
        Object[] parameterNameArray = {"partyId", "metadataId", "metadataName", "inputType", "keyColumn","selectedColumns"};
        return parameterNameArray;
    }
}
