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
 * @Desc 40003: binary格式 (普通的二进制数据, 没有明确说明后缀的二进制文件) (有消费方式)
 * "{
 * "partyId": "p0",
 * "metadataId": "metadata:0xf7396b9a6be9c20...c54880c2d",
 * "metadataName": "aaa",
 * "inputType": 1, // 输入数据的类型，0:unknown, 1:origin_data, 2:psi_output, 3:model
 * "consumeTypes": [],  // 消费该元数据的方式类型说明, 0: unknown, 1: metadataAuth, 2: ERC20, 3: ERC721, ...
 * "consumeOptions": ["具体看消费说明option定义的字符串"]
 * }"
 */

@Getter
@Setter
@ToString
public class DataPolicyOption40003 extends BaseDataPolicyOption {

    private String partyId;
    private String metadataId;
    private String metadataName;
    private Integer inputType;
    private List<Integer> consumeTypes = new ArrayList<>();
    private List<String> consumeOptions = new ArrayList<>();

    @Override
    public Object[] getParameterNameArray() {
        Object[] parameterNameArray = {"partyId", "metadataId", "metadataName", "inputType", "consumeTypes", "consumeOptions"};
        return parameterNameArray;
    }

}
