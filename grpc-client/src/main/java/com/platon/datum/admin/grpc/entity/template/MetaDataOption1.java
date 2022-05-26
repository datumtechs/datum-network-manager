package com.platon.datum.admin.grpc.entity.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * type: Base.MetadataType.MetadataType_DataFile
 * <p>
 * <p>
 * option:
 * <p>
 * {
 * "originId": "d9b41e7138544c63f9fe25f6aa4983819793e5b46f14652a1ff1b51f99f71783",
 * "dataPath": "/home/user1/data/data_root/bank_predict_partyA_20220218-090241.csv",
 * "rows": 100,
 * "columns": 27,
 * "size": "7711",
 * "hasTitle": true,
 * "metadataColumns": [
 * {
 * "index": 1,
 * "name": "CLIENT_ID",
 * "type": "string",
 * "size": 0,
 * "comment": ""
 * }
 * ],
 * }
 */

@Getter
@Setter
@ToString
public class MetaDataOption1 {

    //原始文件ID
    private String originId;
    //原始文件路径
    private String dataPath;
    //原始文件行数
    private long rows;
    //原始文件列数
    private long columns;
    //原始文件大小
    private long size;
    //是否有标题
    private boolean hasTitle;
    //原始文件列信息
    private List<MetadataColumn> metadataColumns;

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
