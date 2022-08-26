package com.platon.datum.admin.grpc.entity.template;

import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author liushuyu
 * @date 2022/8/26 17:35
 * @desc <pre>
 *     任务的元数据消费option说明
 *
 * 0、 unknown
 * 1、 metadataAuth 方式消费
 *
 * "metadataAuth:0xaaaa...ffff"   // 被花费的数据授权Id
 *
 * 2、 ERC20 方式消费
 *
 * "{
 * 	"contract": "0xefefef...ffff",
 * 	"balance": 12222
 * }"
 *
 * 3、 ERC721 方式消费
 *
 * "{
 * 	"contract": "0xefefef...ffff",
 * 	"tokenId": "#12"
 * }"
 * </pre>
 */

@Getter
@Setter
@ToString
public class DataPolicyOptionConsumeOption {

    private String contract;

    public static void main(String[] args) {
        String jsonStr = "{\"consumeOptions\":[\"{\\\"tokenId\\\":\\\"1\\\",\\\"contract\\\":\\\"0xb1766462820e76d111bfa366bb59a4f483ab03c7\\\"}\"],\"consumeTypes\":[3],\"inputType\":1,\"keyColumn\":1,\"metadataId\":\"metadata:0x08cd5ac24903f59a72e8d3cc9b31e94ae6405efeae8c49540d3f159c6277dcd7\",\"metadataName\":\"breast_cancel_train_partyAAAAA\",\"partyId\":\"data1\",\"selectedColumns\":[3]}";
        DataPolicyOption40001 dataPolicyOption40001 = JSONUtil.toBean(jsonStr, DataPolicyOption40001.class);
        List<String> consumeOptions = dataPolicyOption40001.getConsumeOptions();
        StringJoiner contract = new StringJoiner(",");
        consumeOptions.forEach(consumeOption -> {
            DataPolicyOptionConsumeOption dataPolicyOptionConsumeOption = JSONUtil.toBean(consumeOption, DataPolicyOptionConsumeOption.class);
            contract.add(dataPolicyOptionConsumeOption.getContract());
        });
        System.out.println(contract);
    }
}
