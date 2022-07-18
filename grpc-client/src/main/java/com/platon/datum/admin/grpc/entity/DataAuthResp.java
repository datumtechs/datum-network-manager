package com.platon.datum.admin.grpc.entity;

import com.platon.datum.admin.dao.entity.DataAuth;
import lombok.Data;

import java.util.List;


@Data
public class DataAuthResp extends CommonResp{
    private List<DataAuth> dataAuthList;
}
