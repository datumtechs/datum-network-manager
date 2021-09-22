package com.platon.metis.admin.grpc.entity;

import com.platon.metis.admin.dao.entity.LocalDataAuth;
import lombok.Data;

import java.util.List;


@Data
public class DataAuthResp extends CommonResp{
    private List<LocalDataAuth> dataAuthList;
}
