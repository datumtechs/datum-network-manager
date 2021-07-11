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
public class LocalDataFileDetail extends LocalDataFile{
    //源文件列信息
    private List<LocalMetaDataColumn> localMetaDataColumnList = new ArrayList<>();
}
