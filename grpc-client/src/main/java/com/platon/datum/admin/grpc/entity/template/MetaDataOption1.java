package com.platon.datum.admin.grpc.entity.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * <pre>
 *     {
 *  "originId": "d9b41e7138544c63f9fe25f6aa4983819793e5b46f14652a1ff1b51f99f71783",
 *  "dataPath": "/home/user1/data/data_root/bank_predict_partyA_20220218-090241.csv",
 *  "size": 12,
 *  "rows": 100,
 *  "columns": 27,
 *  "hasTitle": true,
 *  "condition": 3,
 *  "metadataColumns": [
 *  {
 *  "index": 1,
 *  "name": "CLIENT_ID",
 *  "type": "string",
 *  "size": 0,
 *  "comment": ""
 *  }
 *  ],
 *  "consumeTypes":[1,2,3] //1、metadataAuth 消费，2、ERC20 消费，3、ERC721 消费（注意： 这个数组中的数字不可以重复出现，且数组中每个元素要和consumeOptions严格对应）
 *  "consumeOptions": [
 *  "[]",
 *  "[{
 *  "contract": "0xbbb...eee", // ERC20合约地址
 *  "cryptoAlgoConsumeUnit": 1000000,  // 用于密文算法的定价单位 (token个数)
 *  "plainAlgoConsumeUnit": 1 // 用于明文算法的定价单位 (token个数)
 *  },
 *  ...,
 *  {
 *  "contract": "0xbbb...eee", // ERC20合约地址
 *  "cryptoAlgoConsumeUnit": 1000000, // 用于密文算法的定价单位 (token个数)
 *  "plainAlgoConsumeUnit": 1 // 用于明文算法的定价单位 (token个数)
 *  }
 *  ]",
 *  "["0xaaa...fff", ..., "0xbbb...eee"]"
 *  ]
 *  }
 * </pre>
 */

@Getter
@Setter
@ToString
public class MetaDataOption1 {

    //原始文件ID
    private String originId;
    //原始文件路径
    private String dataPath;
    //原始文件大小
    private long size;
    //原始文件行数
    private long rows;
    //原始文件列数
    private long columns;
    //是否有标题
    private boolean hasTitle;
    /**
     * 表示数据的一些使用条件,以32bit位设置值, 各个bit上的值可以并存
     * 000...0001==>1 支持明文算法
     * 000...0010==>2 支持密文算法
     * 000...0100==>4 支持下载(支持对外暴露)
     */
    private Integer condition;
    //原始文件列信息
    private List<MetadataColumn> metadataColumns;
    //1、metadataAuth 消费，2、ERC20 消费，3、ERC721 消费（注意： 这个数组中的数字不可以重复出现，且数组中每个元素要和consumeOptions严格对应）
    private List<Integer> consumeTypes;
    //可消费的选项，每一个721的tokenID都要记录进去
    private List<String> consumeOptions;


    @Getter
    @Setter
    @ToString
    public static class MetadataColumn {
        private int index;

        private String name;

        private String type;

        private long size;

        private String comment;
    }
}
