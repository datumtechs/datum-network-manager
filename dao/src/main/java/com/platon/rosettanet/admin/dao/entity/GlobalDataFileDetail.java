package com.platon.rosettanet.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/10 14:37
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class GlobalDataFileDetail extends GlobalDataFile {
    //源文件列信息
    private List<GlobalMetaDataColumn> metaDataColumnList = new ArrayList<>();
}
