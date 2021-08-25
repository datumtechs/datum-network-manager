package com.platon.rosettanet.admin.grpc.entity;

import com.platon.rosettanet.admin.dao.entity.LocalDataAuth;
import lombok.Data;
import java.util.List;


@Data
public class DataAuthResp extends CommonResp{
    private List<LocalDataAuth> dataAuthList;
}
